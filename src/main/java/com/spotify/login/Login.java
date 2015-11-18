package com.spotify.login;

import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;

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
    return Response.forPayload("FAILURE");
  }
}
