from libs.API.model.information_request.information_request import InformationRequest
from libs.API.model.information_request.rfi_http_requests import UploadRFIHttp, SearchRFIHttp
from libs.API.model.information_request.rfi_search_response import \
    RFISearchResponse
from libs.API.requests_builder.files_former import Attachments, \
    JsonAttachment, FileAttachment
from libs.API.requests_builder.request_manager import RequestManager


class RFIService(object):
    """
    Controller class to manipulate with RFis. Supports all methods that API
    has. Call appropriate method of controller to call API method.
    """

    def __init__(self, context):
        self.context = context
        self.request_manager = RequestManager(context)

    def create_rfi(self, rfi_query, approved=None, original=None):
        """
        Analog for API method rfis/upload. Sends request with specified
        user type and information_request obj. Send request to API to create an information_request passed in
        params
        :param user_type: string. user type on behalf of passed user_type.
        Accepts existing in Aurelia user_types
        :param information_request: information_request object with type:class:RFI.
        :param approved: boolean. Set to True if 'approved' file should be sent
        within information_request
        :param original: boolean. Set to True if 'original' file should be sent
        within information_request
        :return: information_request obj with type:class:RFI deserialized from response json
        """
        rfi_request = UploadRFIHttp(self.context)
        files_former = Attachments(JsonAttachment(rfi_query))
        if approved:
            files_former.add_attachment(FileAttachment("approved"))
        if original:
            files_former.add_attachment(FileAttachment("original"))
        with files_former as files:
            self.context.logger.info("Sending upload request {}".format(rfi_query))
            response = self.request_manager.send_request(
                    rfi_request, files=files.files)
            if response.status_code is not 200:
                raise AssertionError("Upload information_request request was unsuccessfull."
                                     "Return code: {}".format(
                                      response.status_code))
        response_rfi = InformationRequest(**response.json()['result'])
        self.context.logger.info(
                "Request was sent successfully. Parsing results...")

        return response_rfi

    def search_for_rfi(self, search):
        rfi_search = SearchRFIHttp(self.context)
        rfi_search.json = search
        response = self.request_manager.send_request(rfi_search)
        if response.status_code is not 200:
            raise AssertionError("Search_request_wasn't performed. Return"
                                 "code {}".format(response.status_code))
        search_response = \
            RFISearchResponse(self.context, **response.json()['result'])

        return search_response