from datetime import datetime

from behave import *
from libs.API.model.information_request.rfi_search_request import RFISearchRequest
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
    rfi_search = RFISearchRequest(**param_dict)
    rfi_service = RFIService(context)
    rfi_service.search_for_rfi(rfi_search)


@then('I can find RFI using todays max respond time')
def find_with_today_last_respond_max(context):
    rfi_search = RFISearchRequest(min_last_respond_date=datetime.utcnow().strftime(date_str_format))
    rfi_service = RFIService(context)
    rfi_service.search_for_rfi(rfi_search)





def __send_rfi(context, rfi=None, **kwargs):
    rfi_service = RFIService(context)
    new_rfi = rfi_service.create_rfi(rfi, **kwargs)
    assert new_rfi.id
    assert new_rfi.internalRequestNumber
    assert new_rfi.createdAt
    assert new_rfi.createdBy
    assert new_rfi.modifiedAt
    context.rfis.add_rfi(new_rfi)


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