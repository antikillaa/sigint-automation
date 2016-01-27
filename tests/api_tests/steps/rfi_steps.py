from behave import *
from libs.API.model.entities_types import EntitiesTypes
from libs.API.model.information_request.information_request_checker import \
    InformationRequestChecker
from libs.API.model.information_request.information_request_query import InformationRequestQuery
from libs.API.model.information_request.rfi_search_request import RFISearchRequest
from libs.API.services.rfi_service import RFIService


@when('I create new RFI with default values')
def create_rfi_with_data(context):
    rfi = InformationRequestQuery()
    __send_rfi(context, rfi)


@when('I create new RFI with specific values')
def create_rfi_with_specific_data(context):
    row = context.table.rows[0]
    param_dict = dict()
    for name in row.headings:
        param_dict[name] = row[name]
    rfi = InformationRequestQuery(**param_dict)
    __send_rfi(context, rfi)


@then('I can find RFI using specific search request')
def find_rfi_with_search_request(context):
    row = context.table.rows[0]
    param_dict = dict()
    for name in row.headings:
        param_dict[name] = row[name]
    rfi_search = RFISearchRequest(**param_dict)
    rfi_service = RFIService(context)
    response = rfi_service.search_for_rfi(rfi_search)


def __send_rfi(context, rfi):
    context.logger.info("Expecting api to create RFI: {}".format(rfi))
    rfi_service = RFIService(context)
    new_rfi = rfi_service.create_rfi(rfi)
    context.query = rfi
    context.new = new_rfi


@when('I update record with files')
def send_update_request(context):
    rfi = context.rfis.get_latest()
    rfi_service = RFIService(context)
    new_rfi = rfi_service.create_rfi(rfi, approved=True, original=True)
    context.query = rfi
    context.new = new_rfi


@then('New information request record is created')
def new_rfi_record_is_created(context):
    InformationRequestChecker.check(context.query, context.new)
    rfi_obj = context.entities.clone(EntitiesTypes.InformationRequest,
                                     context.new)
    context.rfis.add_rfi(rfi_obj)


@then('Information request record has attached files')
def rfi_record_has_attached_files(context):
    rfi = context.rfis.get_latest()
    assert rfi.approvedCopy['filename'] == 'approved'
    assert rfi.originalDocument['filename'] == 'original'


@when('I signed in as "{user_type}" user')
def signed_in_as_user(context, user_type):
    context.auth_token = context.auth_manager.get_token(user_type)