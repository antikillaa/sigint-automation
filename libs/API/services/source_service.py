from libs.API.model.source_types.source import Source
from libs.API.model.source_types.source_http_requests import SourceListHttp
from libs.API.requests_builder.request_manager import RequestManager


class SourceService(object):

    def __init__(self, context):
        self.context = context
        self.request_manager = RequestManager(context)

    def load_services(self):
        list_http = SourceListHttp(self.context)
        response = self.request_manager.send_request(list_http)['result']
        for source in response:
            self.context.sources.add_source(Source(**source))
