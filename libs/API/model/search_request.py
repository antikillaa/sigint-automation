from libs.utils.lazy_property import lazyprop


class SearchRequest(dict):
    page = 0
    pageSize = 50

    def __init__(self, **kwargs):
        super(SearchRequest, self).__init__(pageSize=self.pageSize,
                                            page=self.page, **kwargs)