package com.pettermahlen.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A fake user store that simplifies testing without having to worry about implementation. If one
 * would use a mock instead of this, then all tests would have to know which of the two methods
 * the implementation chooses, or set up the right expectations for both. This leads to:
 *
 * - duplication between tests
 * - having to make many test code changes for a single production code change
 *
 * A fake is often a better choice because it leads to better/more change-resistant test code.
 */
public class FakeUserStore implements UserStore {
  private final Map<String, User> users;

  public FakeUserStore() {
    users = new HashMap<>();
  }

  public void addUser(User user) {
    users.put(user.userName(), user);
  }

  @Override
  public Optional<User> findByName(String userName) {
    return Optional.ofNullable(users.get(userName));
  }

  @Override
  public User findExistingByName(String userName) throws NotFoundException {
    Optional<User> found = findByName(userName);

    if (!found.isPresent()) {
      throw new NotFoundException();
    }

    return found.get();
  }
}
