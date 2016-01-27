from datetime import datetime
from libs.API.model.information_request.information_request import InformationRequest
from libs.API.model.information_request.rfi_data import RFIData
from libs.utils.randomizer import select_random_item, random_string
from libs.utils.unix_time_stamp import time_stamp


class RFIs(object):

    def __init__(self):
        self._rfis = list()

    def add_rfi(self, rfi):
        for i_rfi in self._rfis:
            if rfi.id == i_rfi.id:
                i_rfi.update(rfi)
                return
        self._rfis.append(rfi)

    def delete_rfi(self, rfi):
        if not isinstance(rfi, InformationRequestQuery):
            raise TypeError("argument must have type RFI")
        for l_rfi in self._rfis:
            if l_rfi.id == rfi.id:
                self._rfis.remove(l_rfi)

    def get_latest(self):
        return self._rfis[-1]


class InformationRequestQuery(InformationRequest):
    """
    Describes RFI entity as per request. Set of field reflect update request
    body
    """

    def __init__(self, state=None, priority=None, ext_id=None, req_source=None,
                 distribution_list=None, task_categories=None, subject=None,
                 search_type=None, req_details=None, goals=None,
                 created_date=None):
        super(InformationRequestQuery, self).__init__()
        self.createDate = time_stamp(datetime.strptime(
                created_date, "%d.%m.%Y")) if created_date else \
            time_stamp(datetime.utcnow())
        self.description = req_details
        self.distributionList = distribution_list
        self.externalRequestNumber = ext_id or random_string(s_len=5)
        self.goals = goals
        self.priority = priority if priority in RFIData.priorities else \
                        select_random_item(RFIData.priorities.keys())
        self.requestSource = req_source or random_string()
        self.searchType = search_type if search_type in RFIData.search_types \
                              else select_random_item(RFIData.search_types)
        self.state = state or RFIData.states[0]
        self.subject = subject or random_string()
        self.taskCategories = task_categories
