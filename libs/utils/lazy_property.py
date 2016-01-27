def lazyprop(fn):
    __attr_name = fn.__name__
    @property
    def _lazyprop(self):
        if (__attr_name not in self.keys()) and (__attr_name in self.server_data.keys()):
            return self.server_data[__attr_name]
        elif (__attr_name not in self.keys()) and (__attr_name not in self.server_data.keys()):
            self[__attr_name] = None
        return self[__attr_name]

    return _lazyprop


