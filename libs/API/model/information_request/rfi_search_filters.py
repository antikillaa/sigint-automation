from datetime import datetime
from libs.API.model.search_filter import SearchFilter
from settings import date_str_format

"""
Filters for RFI Search
"""


class MinPriorityFilter(SearchFilter):

    description = 'Min priority filter'

    def check(self, information_request):
        return self._value >= information_request.priority


class StateFilter(SearchFilter):

    description = "State Filter"

    def check(self, information_request):
        return self._value == information_request.state


class RequestSourceFilter(SearchFilter):

    description = "Request Source Filter"

    def check(self, information_request):
        return self._value == information_request.requestSource


class CreatedByFilter(SearchFilter):

    def check(self, information_request):
        return self._value == information_request.createdBy


class MinCreatedDateFilter(SearchFilter):

    description = "Minimal Created Date Filter"

    def check(self, information_request):
        filter_time = datetime.strptime(self._value, date_str_format)
        return information_request.createdDate >= filter_time


class MaxCreatedDateFilter(SearchFilter):

    description = "Max Created Date Filter"

    def check(self, information_request):
        filter_time = datetime.strptime(self._value, date_str_format)
        return information_request.createdDate <= filter_time


class MinDueDateFilter(SearchFilter):

    description = "Minimal Due Date Filter"

    def check(self, information_request):
        filter_time = datetime.strptime(self._value, date_str_format)
        return information_request.dueDate >= filter_time


class MaxDueDateFilter(SearchFilter):

    description = "Max Due Date Filter"

    def check(self, information_request):
        filter_time = datetime.strptime(self._value, date_str_format)
        return information_request.dueDate <= filter_time


class MinLastRespondDateFilter(SearchFilter):

    description = "Min Last Respond Date Filter"

    def check(self, information_request):
        filter_time = datetime.strptime(self._value, date_str_format)
        return information_request.modifiedAt >= filter_time


class MaxLastRespondDateFilter(SearchFilter):

    description = "Max Last Respond Date Filter"

    def check(self, information_request):
        filter_time = datetime.strptime(self._value, date_str_format)
        return information_request.modifiedAt <= filter_time


class RequestNumberFilter(SearchFilter):

    description = "Request Number Filter"

    def check(self, information_request):

        return information_request.internalRequestNumber.startswith(self._value) or \
               information_request.externalRequestNumber.startswith(self._value)


class SubjectFilter(SearchFilter):

    description = "Subject Filter"

    def check(self, information_request):
        return information_request.subject.startswith(self._value)