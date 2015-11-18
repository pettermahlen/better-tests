package com.spotify.login;

/**
 * TODO: document!
 */
public interface UserStore {

  User findByName(String userName);
}
