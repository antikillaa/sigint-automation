from libs.API.requests_builder.base_builder import BaseBuilder
from libs.utils.randomizer import random_email


class SignupRequestBuilder(BaseBuilder):

    def build_data(self):
        self.context.logger.info("There is not body for Signup request")
        return {}

    def build_url(self):
        url = self.context.config.userdata.get("environment") + \
                                                "/sigint/api/auth/signup"
        return url

    def build_json(self):
        email = random_email()
        password = self.context.config.userdata.get("user.password")
        json = {"email": email,
                "password": password}
        return json

    def build_headers(self):
        headers = {'Content-Type': 'application/json'}
        return headers

    def __init__(self, context):
        super(SignupRequestBuilder, self).__init__(context)
        self.request_type = 'post'