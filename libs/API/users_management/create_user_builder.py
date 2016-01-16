from libs.API.requests_builder.base_builder import BaseBuilder
from libs.utils.randomizer import random_email, random_int, random_string


class CreateUserBuilder(BaseBuilder):

    def build_data(self):
        return None

    def build_url(self):
        url = self.context.config.userdata.get("environment") + \
                                               '/sigint/api/admin/user/add'
        return url

    def build_json(self):
        user_dict = dict(
            email=random_email(),
            user_id=random_int(),
            password=self.context.config.userdata.get("user.password"),
            username=random_string(),
            roles=[self.user_type]
        )
        return user_dict

    def build_headers(self):
        headers = {'token': self.build_token(),
                   'Content-Type': 'application/json'}
        return headers

    def __init__(self, context, user_type):
        super(CreateUserBuilder, self).__init__(context)
        self.request_type = 'put'
        self.user_type = user_type
