from abc import ABCMeta, abstractmethod


class SearchFilter:

    __metaclass__ = ABCMeta

    def __init__(self, value=None):
        self._value = value

    @property
    def value(self):
        return self._value

    @value.setter
    def value(self, val):
        self._value = val

    @abstractmethod
    def check(self, obj):
        pass
