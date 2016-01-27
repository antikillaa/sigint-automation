from libs.utils.lazy_property import lazyprop
from datetime import datetime, timedelta
from libs.API.model.information_request.rfi_data import RFIData
from libs.utils.unix_time_stamp import time_stamp


class InformationRequest(dict):

    def __init__(self, iterable=None, **kwargs):
        super(InformationRequest, self).__init__(iterable=iterable, **kwargs)
        self.server_data = dict(
            createdAt=None,
            createdBy=None,
            version=None,
            modifiedBy=None,
            modifiedAt=None,
            originalDocument=None,
            approvedCopy=None)

    @lazyprop
    def dueDate(self):
        return self['dueDate']

    @dueDate.setter
    def dueDate(self, value):
        if value:
            self['dueDate'] = value

    @lazyprop
    def state(self):
        return self['state']

    @state.setter
    def state(self, value):
        if value:
            self['state'] = value

    @lazyprop
    def priority(self):
        return self['priority']

    @priority.setter
    def priority(self, value):
        if value:
            self['priority'] = value
            self.dueDate = time_stamp(datetime.utcnow() + timedelta(
                days=RFIData.priorities[self.priority]))

    @lazyprop
    def searchType(self):
        return self['searchType']

    @searchType.setter
    def searchType(self, value):
        if value:
            self['searchType'] = value

    @lazyprop
    def externalRequestNumber(self):
        return self['externalRequestNumber']

    @externalRequestNumber.setter
    def externalRequestNumber(self, value):
        if value:
            self['externalRequestNumber'] = value

    @lazyprop
    def requestSource(self):
        return self['requestSource']

    @requestSource.setter
    def requestSource(self, value):
        if value:
            self['requestSource'] = value

    @lazyprop
    def distributionList(self):
        return self['distributionList']

    @distributionList.setter
    def distributionList(self, value):
        if value:
            self['distributionList'] = value

    @lazyprop
    def taskCategories(self):
        return self['taskCategories']

    @taskCategories.setter
    def taskCategories(self, value):
        if value:
            self['taskCategories'] = value

    @lazyprop
    def subject(self):
        return self['subject']

    @subject.setter
    def subject(self, value):
        if value:
            self['subject'] = value

    @lazyprop
    def description(self):
        return self['description']

    @description.setter
    def description(self, value):
        if value:
            self['description'] = value

    @lazyprop
    def goals(self):
        return self['goals']

    @goals.setter
    def goals(self, value):
        if value:
            self['goals'] = value

    @lazyprop
    def originalDocument(self):
        return self.server_data['originalDocument']

    @originalDocument.setter
    def originalDocument(self, value):
        if value:
            self.server_data['originalDocument'] = value

    @lazyprop
    def approvedCopy(self):
        return self.server_data['approvedCopy']

    @approvedCopy.setter
    def approvedCopy(self, value):
        if value:
            self.server_data['approvedCopy'] = value

    @lazyprop
    def createdDate(self):
        return self['createdDate']

    @createdDate.setter
    def createdDate(self, value):
        if value:
            self['createdDate'] = value

    @lazyprop
    def createdAt(self):
        return self.server_data['createdAt']

    @createdAt.setter
    def createdAt(self, value):
        if value:
            self.server_data['createdAt'] = value

    @lazyprop
    def createdBy(self):
        return self.server_data['createdBy']

    @createdBy.setter
    def createdBy(self, value):
        if value:
            self.server_data['createdBy'] = value

    @lazyprop
    def id(self):
        return self['id']

    @id.setter
    def id(self, value):
        if value:
            self['id'] = value

    @lazyprop
    def modifiedAt(self):
        return self.server_data['modifiedAt']

    @modifiedAt.setter
    def modifiedAt(self, value):
        if value:
            self.server_data['modifiedAt'] = value

    @lazyprop
    def modifiedBy(self):
        return self.server_data['modifiedBy']

    @modifiedBy.setter
    def modifiedBy(self, value):
        if value:
            self.server_data['modifiedBy'] = value

    @lazyprop
    def internalRequestNumber(self):
        return self['internalRequestNumber']

    @internalRequestNumber.setter
    def internalRequestNumber(self, value):
        if value:
            self['internalRequestNumber'] = value

    @lazyprop
    def previousRequests(self):
        return self['previousRequests']

    @previousRequests.setter
    def previousRequests(self, value):
        if value:
            self['previousRequests'] = value
