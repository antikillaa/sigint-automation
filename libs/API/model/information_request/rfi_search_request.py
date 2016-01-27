from libs.API.model.information_request.rfi_data import RFIData
from libs.API.model.search_request import SearchRequest


class RFISearchRequest(SearchRequest):

    def __init__(self,  sort_direction=False,
                 sort_field='createdAt', states=RFIData.states,
                 state=None, min_priority=None, request_source=None,
                 created_by=None, min_created_date=None, max_created_date=None,
                 min_due_date=None, max_due_date=None,
                 min_last_respond_date=None, max_last_respond_date=None,
                 request_number=None, subject=None):

        super(RFISearchRequest, self).__init__(
            sortDirection=sort_direction,
            sortField=sort_field,
            states=states,
            state=state,
            minPriority=min_priority,
            requestSource=request_source,
            createdBy=created_by,
            minCreatedDate=min_created_date,
            maxCreatedDate=max_created_date,
            minDueDate=min_due_date,
            maxDueDate=max_due_date,
            minLastRespondDate=min_last_respond_date,
            maxLastRespondDate=max_last_respond_date,
            requestNumber=request_number,
            subject=subject

        )

    @property
    def sort_direction(self):
        return self['sortDirection']

    @sort_direction.setter
    def sort_direction(self, value):
        self['sortDirection'] = value

    @property
    def sort_field(self):
        return self['sortField']

    @sort_field.setter
    def sort_field(self, value):
        self['sortField'] = value

    @property
    def states(self):
        return self['states']

    @states.setter
    def states(self, value):
        self['states'].append(value)

    @property
    def state(self):
        return self['state']

    @state.setter
    def state(self, value):
        self['state'] = value

    @property
    def min_priority(self):
        return self['minPriority']

    @min_priority.setter
    def min_priority(self, value):
        self['minPriority'] = value

