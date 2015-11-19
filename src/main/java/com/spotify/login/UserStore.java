package com.spotify.login;

import java.util.Optional;

/**
 * TODO: document!
 */
public interface UserStore {

  Optional<User> findByName(String userName);
}
