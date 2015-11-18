package com.spotify.login;

import com.spotify.apollo.RequestContext;

import static java.util.Objects.requireNonNull;

/**
 * Main service class
 */
public class Login {
  private final UserStore userStore;

  public Login(UserStore userStore) {
    this.userStore = requireNonNull(userStore);
  }

  public String authenticate(RequestContext requestContext) {
    return "FAILURE";
  }
}
