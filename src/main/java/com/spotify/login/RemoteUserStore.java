package com.spotify.login;

import com.spotify.apollo.Client;

/**
 * TODO: document!
 */
public class RemoteUserStore implements UserStore {
  private final Client client;

  public RemoteUserStore(Client client) {
    this.client = client;
  }

  @Override
  public User findByName(String userName) {
    throw new UnsupportedOperationException();
  }
}
