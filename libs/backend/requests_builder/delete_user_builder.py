from libs.backend.requests_builder.base_builder import BaseBuilder


class DeleteUserBuilder(BaseBuilder):

    def __init__(self, context, user):
        super(DeleteUserBuilder, self).__init__(context)
        self.request_type = 'get'
        self.user = user

    def build_headers(self):
        headers = {'token': self.build_token()}
        return headers

    def build_data(self):
        self.context.logger.info("No data required for current requiest")
        return None

    def build_url(self):
        url = self.context.config.userdata.get("environment") + \
                            "/sigint/api/admin/user/{}".format(self.user['id'])

        return url

    def build_json(self):
        self.context.logger.info("No json required for current requiest")
        return None