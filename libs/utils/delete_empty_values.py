def delete_empty(func):
    def deleter(*args, **kwargs):
        u_dict = func(*args, **kwargs)
        for key, value in u_dict.items():
            if not value:
                del u_dict[key]
        return u_dict
    return deleter