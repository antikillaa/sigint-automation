from libs.API.model.aurelia_entity import AureliaEntity
from libs.API.model.rfi import RFI
from libs.API.model.rfi_requests import UploadRFIHttp
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
        user type and rfi obj. RFI obj contains required data in json format.
        To get If successful, add new rfi to the list of
        existing RFis. Note, that for every test run, RFIs collection is reset
        :param user_type: string. user type on behalf of passed user_type.
        Accepts existing in Aurelia user_types
        :param rfi: rfi object with type:class:RFI.
        :return:
        """
        rfi_request = UploadRFIHttp(self.context, user_type)
        with UploadRFIHttp.build_files(rfi) as files:
            self.context.logger.info("Sending upload request")
            response = self.request_manager.send_request(
                    rfi_request, files=files.files)
            if response.status_code is not 200:
                raise AssertionError("Upload rfi request was unsuccessfull."
                                     "Return code: {}".format(
                                      response.status_code))
        self.context.logger.info(
                "Request was sent successfully. Parsing results...")
        request_obj = AureliaEntity.decode(rfi, response.json()['result'])

        return request_obj
