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