from libs.API.controllers.rfis_controller.entities.rfi import RFI
from libs.API.controllers.rfis_controller.helpers.files_request_former import \
    FilesFormer
from libs.API.controllers.rfis_controller.requests.upload_rfi import \
    UploadRFIHttp
from libs.API.requests_builder.request_manager import RequestManager


class RFIController(object):
    """
    Controller class to manipulate with RFis. Supports all methods that API
    has. Call appropriate method of controller to call API method.
    """

    def __init__(self, context):
        self.context = context
        self.request_manager = RequestManager(context)

    def create_rfi(self, user_type, **kwargs):
        """
        Analog for API method rfis/upload. Sends request with specified
        user type and **kwargs. If sucessfull, add new rfi to the list of
        existing RFis. Note, that for every test run, RFIs collection is reset
        :param user_type: string. user type on behalf of passed user_type.
        Accepts existing in Aurelia user_types
        :param kwargs: (optional). Accepts arguments as :class:RFI.
        :return:
        """
        self.context.logger.info("Creating RFI to send to API rfis/upload")
        rfi = RFI(**kwargs)
        self.context.logger.info("Created rfi: {}".format(rfi))
        self.__upload_rfi(user_type, rfi)
        self.context.rfis.add_rfi(rfi)

    def update_rfi(self, user_type, rfi):
        self.context.logger.info("Updating existing rfi")
        self.__upload_rfi(user_type, rfi)

    def __upload_rfi(self, user_type, rfi):
        rfi_request = UploadRFIHttp(self.context, user_type)
        with FilesFormer(rfi.json, rfi.origin_document, rfi.approved_copy) \
                as f_former:
            self.context.logger.info("Sending upload request")
            response = self.request_manager.send_request(rfi_request,
                                                         files=f_former.files)
            if response.status_code is not 200:
                raise AssertionError("Upload rfi request was unsuccessfull."
                                     "Return code: {}".format(
                                      response.status_code))
        self.context.logger.info(
                "Request was sent successfully. Parsing results...")
        rfi.decode_json(response.json()['result'])
