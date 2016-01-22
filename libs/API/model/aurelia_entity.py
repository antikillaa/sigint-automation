import copy
from abc import ABCMeta, abstractmethod


class Serialiazible(object):
    __metaclass__ = ABCMeta

    @abstractmethod
    def decode(self, json):
        pass


class AureliaEntity(dict, Serialiazible):

    def __init__(self,  **kwargs):
        super(AureliaEntity, self).__init__( **kwargs)
        self.server_data =dict()

    def decode(self, json):
        """
        Decodes json to appropriate object passed as entity_type
        :param json: json to deserialize object from
        :return: object with type passed as entity_type param
        """
        entity = copy.copy(self)
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
