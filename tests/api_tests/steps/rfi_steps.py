from behave import *

from libs.API.model.rfi import RFI
from libs.API.services.rfi_service import RFIService


@when('Send create request as "{user_type}" user')
def send_create_request_as_user(context, user_type):
    rfi = RFI()
    context.logger.info("Expecting api to create RFI: {}".format(rfi))
    rfi_service = RFIService(context)
    new_rfi = rfi_service.create_rfi(user_type, rfi)
    assert rfi == new_rfi
    context.rfis.add_rfi(new_rfi)


@when('Send update request with files as "{user_type}" user')
def send_update_request_as_user(context, user_type):
    send_create_request_as_user(context, user_type)
    rfi = context.rfis.get_latest()
    rfi_service = RFIService(context)
    rfi.approved_copy = "approved"
    rfi.origin_document = "original"
    rfi_service.create_rfi(user_type, rfi)


@then('New rfi record is created')
def new_rfi_record_is_created(context):
    rfi = context.rfis.get_latest()
    assert rfi.state =='PENDING'
    assert rfi.internal_number
    assert rfi.created_at
    assert rfi.created_by
    assert rfi.id


@then('rfi record has attached files')
def rfi_record_has_attached_files(context):
    rfi = context.rfis.get_latest()
    assert rfi.approved_copy['filename'] == 'approved'
    assert rfi.origin_document['filename'] == 'original'
