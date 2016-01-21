import datetime

epoch = datetime.datetime(1970, 1, 1)


def time_stamp(dt):
    return int((dt - epoch).total_seconds() * 1000.0)