from helpers.metaclasses import Singleton
from libs.backend.auth_token import auth_token
from libs.backend.requests_builder.create_user_builder import CreateUserBuilder
from libs.backend.requests_builder.delete_user_builder import DeleteUserBuilder
from libs.backend.requests_builder.signup_builder import SignupRequestBuilder
from libs.backend.requests_builder.users_list_builder import UsersListBuilder
from libs.backend.requests_types.base_request import BaseRequest


class UserManagement(object):

    __metaclass__ = Singleton

    def __init__(self, context):
        self.context = context
        self._users = list()
        self.__signup_user()

    @property
    def users_list(self):
        return self._users

    @users_list.setter
    def users_list(self, user):
        self._users.append(user)

    def get_user(self, user_role):
        user = self.__is_exist(user_role)
        if not user:
            self.__create_user(user_role)
            return self.get_user(user_role)
        return user

    def delete_user(self, user):
        users_list = BaseRequest(UsersListBuilder(self.context)
                                 ).send_request()['result']

        for user_l in users_list:
            if user_l['email'] == user.email:
                BaseRequest(DeleteUserBuilder(self.context, user_l)
                            ).send_request()

    def __create_user(self, user_role):
        response = BaseRequest(CreateUserBuilder(self.context, user_role)
                               ).send_request()['result']
        try:
            password = self.context.config.userdata.get("user.password")
            self._users.append(SiginUser(response['email'], password,
                               response['roles']))
        except KeyError:
            raise AssertionError("Got unexpected response from API")

    def __signup_user(self):
        resp = BaseRequest(SignupRequestBuilder(self.context)).\
            send_request()
        auth_token.token = resp['result']['token']

    def __is_exist(self, user_role):
        for user in self._users:
            if user_role.lower() in user.roles:
                return user
        return False


class SiginUser(object):

    def __init__(self, email, password, roles):
        self._roles = roles
        self._email = email
        self._password = password
        self._is_logined = False

    @property
    def is_logined(self):
        return self._is_logined

    @is_logined.setter
    def is_logined(self, value):
        self._is_logined = value

    @property
    def email(self):
        return self._email

    @email.setter
    def email(self, value):
        self._email = value

    @property
    def password(self):
        return self._password

    @password.setter
    def password(self):
        return self._password

    @property
    def roles(self):
        return self._roles

    @roles.setter
    def roles(self, value):
        self._roles.append(value)
