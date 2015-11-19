package com.spotify.login;

import com.spotify.apollo.Environment;
import com.spotify.apollo.route.Route;

public final class LoginSetup {
  private LoginSetup() {
    // prevent instantiation
  }

  public static void init(Environment environment) {
    UserStore userStore = new RemoteUserStore(environment.client());
    Login login = new Login(userStore);

    environment.routingEngine()
        .registerRoute(Route.sync("GET", "/login", login::authenticate));
  }
}
