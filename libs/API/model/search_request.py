from libs.utils.lazy_property import lazyprop


class SearchRequest(dict):
    page = 0
    pageSize = 50

    def __init__(self, **kwargs):
        super(SearchRequest, self).__init__(pageSize=self.pageSize,
                                            page=self.page, **kwargs)

    @lazyprop
    def page(self):
        return self['page']

    @page.setter
    def page(self, value):
        if value:
            self['page'] = value

    @lazyprop
    def pageSize(self):
        return self['pageSize']

    @pageSize.setter
    def pageSize(self, value):
        if value:
            self['pageSize'] = value