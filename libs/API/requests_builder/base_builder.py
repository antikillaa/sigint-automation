from abc import ABCMeta, abstractmethod
from libs.backend.auth_token import auth_token


class BaseBuilder(object):

    __metaclass__ = ABCMeta

    def __init__(self, context):
        self.context = context
        self._request_type = None

    @abstractmethod
    def build_url(self):
        pass

    @staticmethod
    def build_token():
        return auth_token.token

    @property
    def request_type(self):
        return self._request_type

    @request_type.setter
    def request_type(self, r_type):
        self._request_type = r_type

    @abstractmethod
    def build_data(self):
        pass

    @abstractmethod
    def build_json(self):
        pass

    @abstractmethod
    def build_headers(self):
        pass