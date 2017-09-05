package data_for_entity.data_providers.user_password;

import data_for_entity.data_providers.EntityDataProvider;
import org.apache.commons.lang3.RandomStringUtils;

import static utils.StringUtils.hasRepeatedCharacters;
import static utils.StringUtils.hasUpperAndLowerCharacters;

public class UserPasswordProvider implements EntityDataProvider {

  private static final int MAX_REPEATED_CHARS = 3;

  @Override
  public String generate(int length) {
    String password;
    do {
      password = RandomStringUtils.randomAlphanumeric(length);
    } while (hasRepeatedCharacters(password, MAX_REPEATED_CHARS) || !hasUpperAndLowerCharacters(password));
    return password;
  }
}