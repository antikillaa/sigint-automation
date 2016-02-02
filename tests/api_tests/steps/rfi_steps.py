from datetime import datetime
from behave import *
from libs.API.model.information_request.information_request_checker import InformationRequestChecker
from libs.API.model.information_request.information_request_manager import InformationRequestManager
from libs.API.model.information_request.rfi_search_checker import RFISearchChecker
from libs.API.model.information_request.rfi_search_request import RFISearchRequest
from libs.API.model.information_request.rfi_search_response import RFISearchResponse
from libs.API.services.rfi_service import RFIService
from settings import date_str_format


@when('I create new RFI with default values')
def create_rfi_with_data(context):
    __send_rfi(context)


@when('I create new RFI with specific values')
def create_rfi_with_specific_data(context):
    row = context.table.rows[0]
    param_dict = dict()
    for name in row.headings:
        param_dict[name] = row[name]
    __send_rfi(context, **param_dict)


@then('I can find RFI using specific search request')
def find_rfi_with_search_request(context):
    row = context.table.rows[0]
    param_dict = dict()
    for name in row.headings:
        param_dict[name] = row[name]
    __search_rfi(context, **param_dict)



@then('I can find RFI using todays max respond time')
def find_with_today_last_respond_max(context):
    rfi_search = RFISearchRequest(min_last_respond_date=datetime.utcnow().strftime(date_str_format))
    rfi_service = RFIService(context)
    rfi_service.search_for_rfi(rfi_search)


def __search_rfi(context, **param_dict):
    rfi_search = RFISearchRequest(**param_dict)
    rfi_service = RFIService(context)
    response = rfi_service.search_for_rfi(rfi_search)
    if response.status_code is not 200:
            raise AssertionError("Search request wasn't performed. Return"
                                 "code {}".format(response.status_code))
    search_response = RFISearchResponse(context, **response.json()['result'])
    context.logger.info("Found objects: {}".format(search_response.found_objects))
    context.logger.debug("Checking results....")
    RFISearchChecker.check(rfi_search, search_response)
    context.logger.debug("Verification successfully completed")

    return search_response

def __send_rfi(context, rfi=None, approved=None, original=None,  **kwargs):
    rfi_service = RFIService(context)
    rfi_manager = InformationRequestManager()
    rfi_request = rfi_manager.to_request(rfi, **kwargs)
    response = rfi_service.create_rfi(rfi_request, approved=approved, original=original)
    if response.status_code is not 200:
                raise AssertionError("Upload information_request request was "
                                     "unsuccessful. Return code: {}".format(response.status_code))
    response_rfi = rfi_manager.to_response(**response.json()['result'])
    InformationRequestChecker.check(rfi_request, response_rfi)
    context.logger.info(
                "Request was sent successfully. Parsing results...")
    assert response_rfi.id
    assert response_rfi.internalRequestNumber
    assert response_rfi.createdAt
    assert response_rfi.createdBy
    assert response_rfi.modifiedAt
    context.logger.info("Information request record was created successfully")
    context.rfis.add_rfi(response_rfi)


@when('I update record with files')
def send_update_request(context):
    rfi = context.rfis.get_latest()
    __send_rfi(context, rfi, approved=True, original=True)


@then('New information request record is created')
def new_rfi_record_is_created(context):
    rfi = context.rfis.get_latest()
    assert rfi


@then('Information request record has attached files')
def rfi_record_has_attached_files(context):
    rfi = context.rfis.get_latest()
    assert rfi.approvedCopy['filename'] == 'approved'
    assert rfi.originalDocument['filename'] == 'original'


@when('I signed in as "{user_type}" user')
def signed_in_as_user(context, user_type):
    context.auth_token = context.auth_manager.get_token(user_type)


@then('I can delete rfi')
def delete_rfi(context):
    rfi = context.rfis.get_latest()
    rfi_service = RFIService(context)
    response = rfi_service.delete_rfi(rfi.id)
    if response.status_code is not 200:
            raise AssertionError("Delete request wasn't performed. Return"
                                 "code {}".format(response.status_code))
    search_response = __search_rfi(context)
    for entity in search_response.found_objects:
        assert not entity.id == rfi.id
    context.rfis.delete_rfi(rfi)
