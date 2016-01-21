from datetime import datetime, timedelta
from libs.API.controllers.rfis_controller.entities.rfi_data import RFIData, \
    RFIDataStates
from libs.utils.delete_empty_values import delete_empty
from libs.utils.randomizer import select_random_item, random_string
from libs.utils.unix_time_stamp import time_stamp


class RFIs(object):

    def __init__(self):
        self._rfis = list()

    def add_rfi(self, rfi):
        self._rfis.append(rfi)

    def delete_rfi(self, rfi):
        if not isinstance(rfi, RFI):
            raise TypeError("argument must have type RFI")
        for l_rfi in self._rfis:
            if l_rfi.id == rfi.id:
                self._rfis.remove(l_rfi)

    def get_latest(self):
        return self._rfis[-1]


class RFI(dict):
    """
    Describes RFI entity as per request. Set of field reflect update request
    body
    """

    def __init__(self, state=None, priority=None, ext_id=None, req_source=None,
                 distribution_list=None, task_categories=None, subject=None,
                 search_type=None, req_details=None, goals=None,
                 origin_doc=None, app_copy=None,created_date=None):
        super(RFI, self).__init__(
            createdAt=None,
            createdBy=None,
            createdDate=created_date or time_stamp(datetime.utcnow()),
            description=req_details,
            distributionList=distribution_list,
            externalRequestNumber=ext_id or random_string(s_len=5),
            goals=goals,
            id=None,
            internalRequestNumber=None,
            modifiedAt=None,
            modifiedBy=None,
            previousRequests=None,
            priority=priority if priority in RFIData.priorities
            else select_random_item(RFIData.priorities.keys()),
            dueDate=None,
            requestSource=req_source or random_string(),
            searchParams=None,
            searchType=search_type if search_type in RFIData.search_types
            else select_random_item(RFIData.search_types),
            state=state or RFIDataStates.PENDING,
            subject=subject or random_string(),
            taskCategories=task_categories,
            origin_doc=None,
            app_copy=None,
            version=None

        )
        self.due_date = time_stamp(datetime.utcnow() + timedelta(
                days=RFIData.priorities[self.priority]))

    @property
    def due_date(self):
        return self['dueDate']

    @due_date.setter
    def due_date(self, value):
        self['dueDate'] = value

    @property
    def state(self):
        return self['state']

    @state.setter
    def state(self, value):
        self['state'] = value

    @property
    def priority(self):
        return self['priority']

    @priority.setter
    def priority(self, value):
        self['priority'] = value

    @property
    def search_type(self):
        return self['searchType']

    @search_type.setter
    def search_type(self, value):
        self['searchType'] = value

    @property
    def external_id(self):
        return self['externalRequestNumber']

    @external_id.setter
    def external_id(self, value):
        self['externalRequestNumber'] = value

    @property
    def request_source(self):
        return self['requestSource']

    @request_source.setter
    def request_source(self, value):
        self['requestSource'] = value

    @property
    def distribution_list(self):
        return self['distributionList']

    @distribution_list.setter
    def distribution_list(self, value):
        self['distributionList'] = value

    @property
    def task_categories(self):
        return self['taskCategories']

    @task_categories.setter
    def task_categories(self, value):
        self['taskCategories'] = value

    @property
    def subject(self):
        return self['subject']

    @subject.setter
    def subject(self, value):
        self['subject'] = value

    @property
    def request_details(self):
        return self['description']

    @request_details.setter
    def request_details(self, value):
        self['description'] = value

    @property
    def goals(self):
        return self['goals']

    @goals.setter
    def goals(self, value):
        self['goals'] = value

    @property
    def origin_document(self):
        return self['origin_doc']

    @origin_document.setter
    def origin_document(self, value):
        self['origin_doc'] = value

    @property
    def approved_copy(self):
        return self['app_copy']

    @approved_copy.setter
    def approved_copy(self, value):
        self['app_copy'] = value

    @property
    def created_date(self):
        return self['createdDate']

    @created_date.setter
    def created_date(self, value):
        self['createdDate'] = value

    @property
    def created_at(self):
        return self['createdAt']

    @created_at.setter
    def created_at(self, value):
        self['createdAt'] = value

    @property
    def created_by(self):
        return self['createdBy']

    @created_by.setter
    def created_by(self, value):
        self['createdBy'] = value

    @property
    def id(self):
        return self['id']

    @id.setter
    def id(self, value):
        self['id'] = value

    @property
    def modified_at(self):
        return self['modifiedAt']

    @modified_at.setter
    def modified_at(self, value):
        self['modifiedAt'] = value

    @property
    def modified_by(self):
        return self['modifiedBy']

    @modified_by.setter
    def modified_by(self, value):
        self['modifiedBy'] = value

    @property
    def internal_number(self):
        return self['internalRequestNumber']

    @internal_number.setter
    def internal_number(self, value):
        self['internalRequestNumber'] = value

    @property
    @delete_empty
    def json(self):
        """
        :return: dict representation of object attributes
        """
        new_dict = dict()
        for key, value in self.iteritems():
            new_dict[key] = value
        return new_dict

    def decode_json(self, json):
        """
        Injects received via request json to current object.
        Error is raised if value from request response doesn't equal to
        request form
        :param json: response got from request
        :return: --
        """
        for key, value in json.iteritems():
            if self[key] is None:
                self[key] = value
            else:
                assert self[key] == value
