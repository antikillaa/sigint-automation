from libs.API.requests_builder.base_builder import BaseBuilder


class UsersListBuilder(BaseBuilder):

    def __init__(self, context):
        super(UsersListBuilder, self).__init__(context)
        self.request_type = 'get'

    def build_headers(self):
        headers = {'token': self.build_token()}
        return headers

    def build_data(self):
        self.context.logger.info("No data required for current requiest")
        return None

    def build_url(self):
        url = self.context.config.userdata.get("environment") + \
              "/sigint/api/admin/users"

        return url

    def build_json(self):
        self.context.logger.info("No json required for current requiest")
        return None