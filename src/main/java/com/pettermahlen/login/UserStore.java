package com.pettermahlen.login;

import java.util.Optional;

/**
 * Defines interactions with the user store. A more complete example would probably have more
 * methods.
 *
 * The two alternative methods defined in this interface are just there to make the point about
 * when fakes are better than mocks. See FakeUserStore for more.
 */
public interface UserStore {

  Optional<User> findByName(String userName);

  User findExistingByName(String userName) throws NotFoundException;
}
