from abc import abstractmethod

class Manager:

    template = None

    def __init__(self):
        pass

    @abstractmethod
    def to_request(self, **kwargs):
        pass

    @abstractmethod
    def to_response(self, **kwargs):
        pass

    @abstractmethod
    def to_object(self, **kwargs):
        pass
