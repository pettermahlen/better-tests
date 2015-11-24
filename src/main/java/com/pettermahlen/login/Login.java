package com.pettermahlen.login;

import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.Status;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Main service class
 */
public class Login {
  private final UserStore userStore;

  public Login(UserStore userStore) {
    this.userStore = requireNonNull(userStore);
  }

  public Response<String> authenticate(RequestContext requestContext) {
    throw new UnsupportedOperationException();
  }
}
