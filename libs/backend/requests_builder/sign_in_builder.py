from libs.backend.requests_builder.base_builder import BaseBuilder


class SignInBuilder(BaseBuilder):

    def build_data(self):
        return None

    def build_url(self):
        url = self.context.config.userdata.get("environment") + \
                                               '/sigint/api/auth/login'
        return url

    def build_json(self):
        user_dict = dict(
            email=self.email,
            password=self.password,
        )
        return user_dict

    def build_headers(self):
        headers = {'token': self.build_token(),
                   'Content-Type': 'application/json'}
        return headers

    def __init__(self, context, email, password):
        super(SignInBuilder, self).__init__(context)
        self.request_type = 'post'
        self.email = email
        self.password = password
