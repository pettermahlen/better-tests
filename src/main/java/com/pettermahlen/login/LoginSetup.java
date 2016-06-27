package com.pettermahlen.login;

import com.spotify.apollo.Environment;
import com.spotify.apollo.route.Route;

/**
 * Does the necessary wiring/dependency injection and route setup.
 */
public final class LoginSetup {
  private LoginSetup() {
    // prevent instantiation
  }

  public static void init(Environment environment) {
    UserStore userStore = new RemoteUserStore(environment.client());
    Login login = new Login(userStore);

    environment.routingEngine()
        .registerAutoRoute(Route.sync("GET", "/login", login::authenticate));
  }
}
