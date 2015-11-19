package com.spotify.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * TODO: document!
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
}
