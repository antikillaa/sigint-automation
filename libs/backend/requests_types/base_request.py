import requests


class BaseRequest(object):

    def __init__(self, request_builder):
        self.request_builder = request_builder
        self.context = self.request_builder.context

    def send_request(self):
        url = self.request_builder.build_url()
        json = self.request_builder.build_json()
        data = self.request_builder.build_data()
        headers = self.request_builder.build_headers()
        response = self.__send_request(
                self.request_builder.request_type, url, json=json,
                data=data, headers=headers).json()

        return response

    def __send_request(self, request_type, url, data=None, json=None,
                       headers=None, **kwargs):
        self.context.logger.info("Sending {0} request to URL {1}".format(
                request_type.upper(), url))
        if request_type.lower() == 'get':
            resp = requests.get(url, headers=headers, **kwargs)
        elif request_type.lower() == 'put':
            print data
            resp = requests.put(url, data=data, json=json, headers=headers,
                                **kwargs)
        elif request_type.lower() == 'post':
            resp = requests.post(url, data=data, json=json,
                                 headers=headers, **kwargs)
        else:
            raise TypeError("Unknown request type - {}".format(request_type))

        self.context.logger.info("Got response on request: "
                                 "{}".format(resp.json()))

        if resp.status_code is not 200:
            self.context.logger.error("Got error on request: {}".format(
                    resp.json()))
            self.context.logger.debug("Actual response code is {}".format(
                    resp.status_code))
            raise AssertionError("Return code is not 200")

        return resp
