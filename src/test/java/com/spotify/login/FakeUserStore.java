package com.spotify.login;

import java.util.HashMap;
import java.util.Map;

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
  public User findByName(String userName) {
    return users.get(userName);
  }
}
