from libs.API.model.entities_types import EntitiesTypes
from libs.API.model.search_response import SearchResponse


class RFISearchResponse(SearchResponse):

    deserialize_object = EntitiesTypes.InformationRequest

    def __init__(self, context, **kwargs):
        super(RFISearchResponse, self).__init__(context,
                                                kwargs['content'], **kwargs)



    @property
    def content(self):
        return self['content']

    @property
    def first(self):
        return self['first']

    @property
    def last(self):
        return self['last']

    @property
    def number(self):
        return self['number']

    @property
    def sort(self):
        return self['sort']