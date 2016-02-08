from libs.API.model.Entities import Entity
from libs.utils.lazy_property import lazyprop


class Record(Entity):

    def __init__(self, **kwargs):
        super(Record, self).__init__(**kwargs)


    @lazyprop
    def duration(self):
        return self['duration']

    @duration.setter
    def duration(self, value):
        self['duration'] = value

    @lazyprop
    def fromCountry(self):
        return self['fromCountry']

    @fromCountry.setter
    def fromCountry(self, value):
        self['fromCountry'] = value

    @lazyprop
    def fromNumber(self):
        return self['fromNumber']

    @fromNumber.setter
    def fromNumber(self, value):
        self['fromNumber'] = value

    @lazyprop
    def imsi(self):
        return self['imsi']