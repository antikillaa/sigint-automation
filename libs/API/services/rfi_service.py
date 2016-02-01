from libs.API.model.information_request.information_request_manager import \
    InformationRequestManager
from libs.API.model.information_request.rfi_http_requests import UploadRFIHttp, SearchRFIHttp
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

    def create_rfi(self, rfi_request, approved=None, original=None):
        """
        Analog for API method rfis/upload. Sends request with specified
        user type and information_request obj. Send request to API to create an
        information_request passed in params
        :param rfi_request: class:InformationRequest. Request to update or create a new RFI. RFI request if formed
        on test level. Service is responsible for sending request only.
        :param approved: boolean. Set to True if 'approved' file should be sent
        within information_request
        :param original: boolean. Set to True if 'original' file should be sent
        within information_request
        :param kwargs: (optional). Accepts arguments as :class:InformationRequest: does
        :return: information_request obj with type:class:InformationRequest deserialized from
        response json
        """

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
        return response

    def search_for_rfi(self, search):
        """
        Send http search request and verifies the output
        :param search: param with type:class:RFISearchRequest
        :return: Found objects via http
        """
        self.context.logger.info("Start searching rfis by search request: {}".format(search))
        rfi_search = SearchRFIHttp(self.context)
        rfi_search.build_json(search)
        response = self.request_manager.send_request(rfi_search)
        return response
