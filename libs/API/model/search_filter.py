from abc import ABCMeta, abstractmethod


class SearchFilter:

    __metaclass__ = ABCMeta

    @abstractmethod
    @property
    def value(self):
        pass

    @abstractmethod
    def check(self, obj):
        pass
