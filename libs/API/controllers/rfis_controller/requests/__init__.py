from libs.API.requests_builder.http_request import HttpRequest


class RFIUpload(HttpRequest):

    def _build_url(self):
        return self._base + 'rfis/upload'

    def __init__(self, context):
        super(RFIUpload, self).__init__(context, 'post')
