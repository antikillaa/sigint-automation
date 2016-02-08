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


class Entity(dict):
    """
    Abstract class to describe entity
    """
    def __init__(self, **kwargs):
        super(Entity, self).__init__(**kwargs)