import copy
from abc import ABCMeta, abstractmethod


class Serialiazible(object):
    __metaclass__ = ABCMeta

    @abstractmethod
    def decode(self, json):
        pass


class AureliaEntity(dict, Serialiazible):

    def __init__(self,  **kwargs):
        super(AureliaEntity, self).__init__(**kwargs)
        self.server_data = dict()

    @staticmethod
    def decode(obj, json):
        """
        Decodes json to appropriate object passed as entity_type
        :param json: json to deserialize object from
        :param obj: object target to deserialize json to.
        Should have type:class:AurliaEntity
        :return: object with type passed as obj
        """
        entity = copy.copy(obj)
        for key, value in json.iteritems():
            if key in entity.server_data.keys():
                entity.server_data[key] = value
            else:
                entity[key] = value
        return entity

    def __eq__(self, other):
        for attribute, value in self.iteritems():
            if not value:
                continue
        if value != other[attribute]:
            return False
        return True
