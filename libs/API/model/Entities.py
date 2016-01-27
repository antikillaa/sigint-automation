import copy


class Entities:

    def __init__(self, context):
        self.context = context
        self._objects = {}

    def register_object(self, name, obj):
        self._objects[name] = obj

    def unregister_object(self, name):
        try:
            del self._objects[name]
        except IndexError:
            self.context.logger.warning(
                    "Object with name {} not found".format(name))

    def clone(self, name, attr):
        try:
            obj = copy.copy(self._objects[name])
        except IndexError:
            raise AssertionError("Object template with name {} not found"
                                 "".format(name))

        for key, value in attr.iteritems():
            if key in attr.server_data.keys():
                obj.server_data[key] = value
            else:
                obj[key] = value
        return obj