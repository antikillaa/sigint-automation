import copy
from libs.API.model.entities import Manager
from libs.API.model.information_request.information_request import \
    InformationRequest
from datetime import datetime, timedelta
from libs.API.model.information_request.rfi_data import RFIData
from libs.utils.randomizer import random_string, select_random_item
from libs.utils.unix_time_stamp import to_time_stamp
from settings import date_str_format


class InformationRequestManager(Manager):

    template = InformationRequest()

    def __parse(self, **kwargs):
        obj = copy.copy(self.template)
        for attribute, value in kwargs.iteritems():
            setattr(obj, attribute, value)
        return obj

    def to_request(self, **kwargs):
        obj = self.__parse(**kwargs)
        obj.createdDate = to_time_stamp(obj.createdDate) if obj.createdDate \
            else to_time_stamp(datetime.utcnow())
        obj.description = obj.description
        obj.distributionList = obj.distributionList
        obj.externalRequestNumber = obj.externalRequestNumber or \
            random_string(s_len=5)
        obj.goals = obj.goals
        obj.priority = obj.priority if obj.priority in RFIData.priorities else \
            select_random_item(RFIData.priorities.keys())
        obj.requestSource = obj.requestSource or random_string()
        obj.searchType = obj.searchType if obj.searchType in \
                                           RFIData.search_types else \
            select_random_item(RFIData.search_types)
        obj.state = obj.state or RFIData.states[0]
        obj.subject = obj.subject or random_string()
        obj.taskCategories = obj.taskCategories
        obj.dueDate = to_time_stamp(datetime.strptime(
                    obj.createdDate, date_str_format) + timedelta(
                    days=RFIData.priorities[obj.priority]))

        return obj

    def to_object(self, **kwargs):
        obj = self.__parse(**kwargs)
        return obj

    def to_response(self, **kwargs):
        obj = self.__parse(**kwargs)
        return obj
