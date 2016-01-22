from libs.API.model.rfi_requests import UploadRFIHttp
from libs.API.requests_builder.files_former import JsonAttachment, \
    FileAttachment, Attachments
from libs.API.requests_builder.request_manager import RequestManager


class RFIService(object):
    """
    Controller class to manipulate with RFis. Supports all methods that API
    has. Call appropriate method of controller to call API method.
    """

    def __init__(self, context):
        self.context = context
        self.request_manager = RequestManager(context)

    def create_rfi(self, user_type, rfi):
        """
        Analog for API method rfis/upload. Sends request with specified
        user type and **kwargs. If successful, add new rfi to the list of
        existing RFis. Note, that for every test run, RFIs collection is reset
        :param user_type: string. user type on behalf of passed user_type.
        Accepts existing in Aurelia user_types
        :param kwargs: (optional). Accepts arguments as :class:RFI.
        :return:
        """
        new_rfi = self.__upload_rfi(user_type, rfi)
        return new_rfi

    def __upload_rfi(self, user_type, rfi):
        rfi_request = UploadRFIHttp(self.context, user_type)
        req_json = JsonAttachment(rfi)
        files_former = Attachments(req_json)
        if rfi.approved_copy:
            files_former.add_attachment(FileAttachment(rfi.approved_copy))
        if rfi.origin_document:
            files_former.add_attachment(FileAttachment(rfi.origin_document))
        with files_former:
            self.context.logger.info("Sending upload request")
            response = self.request_manager.send_request(
                    rfi_request, files=files_former.files)
            if response.status_code is not 200:
                raise AssertionError("Upload rfi request was unsuccessfull."
                                     "Return code: {}".format(
                                      response.status_code))
        self.context.logger.info(
                "Request was sent successfully. Parsing results...")
        request_obj = rfi.decode(response.json()['result'])
        return request_obj
