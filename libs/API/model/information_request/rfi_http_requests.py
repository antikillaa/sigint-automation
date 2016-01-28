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

    def __init__(self, context):
        super(UploadRFIHttp, self).__init__(context, 'post')


class SearchRFIHttp(HttpRequest):
    """
    Describes /rfis/search request
    """

    def _build_url(self):
        """
        Forms url for request
        :return: string url
        """
        url = self._base + 'rfis/search'
        return url

    def __init__(self, context):
        super(SearchRFIHttp, self).__init__(context, 'post')
