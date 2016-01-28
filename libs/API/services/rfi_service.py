from libs.API.model.information_request.information_request import InformationRequest
from libs.API.model.information_request.information_request_checker import \
    InformationRequestChecker
from libs.API.model.information_request.information_request_manager import \
    InformationRequestManager
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
    manager = InformationRequestManager()

    def __init__(self, context):
        self.context = context
        self.request_manager = RequestManager(context)

    def create_rfi(self, approved=None, original=None,
                   **kwargs):
        """
        Analog for API method rfis/upload. Sends request with specified
        user type and information_request obj. Send request to API to create an
        information_request passed in params
        :param user_type: string. user type on behalf of passed user_type.
        Accepts existing in Aurelia user_types
        :param information_request: information_request object with type:class:RFI.
        :param approved: boolean. Set to True if 'approved' file should be sent
        within information_request
        :param original: boolean. Set to True if 'original' file should be sent
        within information_request
        :return: information_request obj with type:class:RFI deserialized from
        response json
        """

        rfi_request = self.manager.to_request(**kwargs)
        request = UploadRFIHttp(self.context)
        attachments = Attachments()
        attachments.add_attachment(JsonAttachment(rfi_request))
        if approved:
            attachments.add_attachment(FileAttachment("approved"))
        if original:
            attachments.add_attachment(FileAttachment("original"))
        with attachments as att:
            response = self.request_manager.send_request(
                    request, files=att.files)
            if response.status_code is not 200:
                raise AssertionError("Upload information_request request was "
                                     "unsuccessful. Return code: {}".format(
                        response.status_code))
        response_rfi = self.manager.to_response(**response.json()['result'])
        InformationRequestChecker.check(rfi_request, response_rfi)
        self.context.logger.info(
                "Request was sent successfully. Parsing results...")

        return self.manager.to_object(**response.json()['result'])

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