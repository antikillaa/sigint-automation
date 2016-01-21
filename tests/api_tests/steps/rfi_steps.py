from behave import *
from libs.API.controllers.rfis_controller.rfi_controller import RFIController


@when('Send "{request_type}" request as "{user_type}" user')
def send_request_as_user(context, request_type, user_type):
    rfi_controller = RFIController(context)
    if request_type.lower() == 'create':
        rfi_controller.upload_rfi(user_type)


@then('New rfi record is created')
def new_rfi_record_is_created(context):
    rfi = context.rfis.get_latest()
    assert rfi.state=='PENDING'
    assert rfi.internal_number