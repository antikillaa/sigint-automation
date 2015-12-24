import random
import string
import exrex as exrex


def random_string(s_len=10, reg_exp=None):
    if reg_exp:
        rand_string = exrex.getone(reg_exp, limit=int(s_len))
        return rand_string[2:-1]
    return ''.join(
        random.choice(string.ascii_letters) for _ in range(int(s_len)))


def random_int(record_len=10):
    return random.randint(10**(record_len-1), 10**record_len-1)


def random_email(user_len=5):
    domains = ["hotmail.com", "gmail.com", "yahoo.com"]

    def get_one_random_domain():
        return random.choice(domains)

    return '@'.join([random_string(int(user_len)), get_one_random_domain()])


def select_random_item(u_list):
    if not isinstance(u_list, list):
        raise AssertionError(
            "Incorrect param is passed to random method! "
            "Should be list type but was {}".format(type(u_list)))
    if not u_list:
        raise AssertionError("Passed list is empty!")
    return random.choice(u_list)

