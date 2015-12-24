import logging
import os
import sys
from selenium import webdriver
from libs.core.users_management.users import UserManagement
from libs.logging.logger import CustomLogger


def before_all(context):
    driver = {
        'darwin': "chromedriver 2",
        'linux': "chromedriver"
    }
    context.chromedriver = "bin/{}".format(driver[sys.platform])
    os.environ['webdriver.chrome.driver'] = context.chromedriver
    context.logger = CustomLogger(context).get_logger()
    context.users = UserManagement(context)


def before_feature(context, feature):
    logging.info("Starting verifying feature {}".format(feature.name))


def after_feature(context, feature):
    logging.info("Finished verifying feature {}".format(feature.name))


def before_step(context, step):
    logging.info("Starting step {}".format(step.name))


def after_step(context, step):
    logging.info("Finishing step {}".format(step.name))


def before_scenario(context, scenario):
    context.driver = webdriver.Chrome(context.chromedriver)
    context.driver.get(context.config.userdata.get("environment"))


def after_scenario(context, scenario):
    context.driver.close()


def after_all(context):
    logging.info("Deleting all created during session user accounts")
    map(context.users.delete_user, context.users.users_list)
