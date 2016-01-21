from behave import *
from libs.API.controllers.rfis_controller.rfi_controller import RFIController


@when('Send create request as "{user_type}" user')
def send_create_request_as_user(context, user_type):
    rfi_controller = RFIController(context)
    rfi_controller.create_rfi(user_type)


@when('Send update request with files as "{user_type}" user')
def send_update_request_as_user(context, user_type):
    rfi_controller = RFIController(context)
    rfi = context.rfis.get_latest()
    rfi.approved_copy = "approved_copy"
    rfi.origin_document = "original_document"
    rfi_controller.update_rfi(user_type, rfi)


@then('New rfi record is created')
def new_rfi_record_is_created(context):
    rfi = context.rfis.get_latest()
    assert rfi.state =='PENDING'
    assert rfi.internal_number

@then('rfi record has attached files')
def rfi_record_has_attached_files(context):
    rfi = context.rfis.get_latest()
    assert rfi.approved_copy['filename'] == 'approved_copy'
    assert rfi.origin_document['filename'] == 'original_document'
