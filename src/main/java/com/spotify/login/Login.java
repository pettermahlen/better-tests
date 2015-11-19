package com.spotify.login;

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
    final Optional<String> userName = requestContext.request().parameter("userName");
    final Optional<String> password = requestContext.request().parameter("password");

    if (!userName.isPresent() || !password.isPresent()) {
      return Response.forStatus(
          Status.BAD_REQUEST
              .withReasonPhrase("Both 'userName' and 'password' query parameters must be present"));
    }

    final Optional<User> user = userStore.findByName(userName.get());

    if (!user.isPresent() || !user.get().password().equals(password.get())) {
      return Response.forPayload("FAILURE");
    }

    return Response.forPayload("SUCCESS");
  }
}
