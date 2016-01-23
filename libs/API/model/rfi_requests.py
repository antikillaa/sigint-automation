from libs.API.requests_builder.files_former import JsonAttachment, Attachments, \
    FileAttachment
from libs.API.requests_builder.http_request import HttpRequest


class UploadRFIHttp(HttpRequest):
    """
    Describes /rfis/upload request
    """

    def _build_url(self):
        """
        Forms url for request
        :return: string url
        """
        url = self._base + 'rfis/upload'
        return url

    def _build_headers(self):
        """
        Forms header for request
        :return: dict headers
        """
        headers = {'token': self.context.auth_manager.get_token(self.user_type)}
        return headers

    def __init__(self, context, user_type):
        super(UploadRFIHttp, self).__init__(context, 'post', user_type)

    @staticmethod
    def build_files(rfi):
        req_json = JsonAttachment(rfi)
        files_former = Attachments(req_json)
        if rfi.approved_copy:
            files_former.add_attachment(FileAttachment(rfi.approved_copy))
        if rfi.origin_document:
            files_former.add_attachment(FileAttachment(rfi.origin_document))

        return files_former
