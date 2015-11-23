package com.pettermahlen.login;

import com.google.common.collect.ImmutableMap;

import com.spotify.apollo.Client;
import com.spotify.apollo.Request;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.request.RequestContexts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.spotify.apollo.test.unit.ResponseMatchers.hasPayload;
import static com.spotify.apollo.test.unit.ResponseMatchers.hasStatus;
import static com.spotify.apollo.test.unit.StatusTypeMatchers.withCode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoginTest {
  private static final String URI_BASE = "http://localhost/login?";

  // the thing we'll be testing
  Login login;

  // a fake, but functional user store.
  FakeUserStore userStore;

  // Here, mocking is a good choice, because we never use this client in our tests.
  // Mocking can be a good choice otherwise as well, but there is a danger of coupling your
  // test code with the production code.
  @Mock
  Client client;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    userStore = new FakeUserStore();

    login = new Login(userStore);
  }

  @Test
  public void shouldReturnSuccessForCorrectCredentials() throws Exception {
    userStore.addUser(User.create("petter", "super-encrypted"));

    RequestContext requestContext = loginAs("petter", "super-encrypted");

    assertThat(login.authenticate(requestContext), hasPayload(is("SUCCESS")));
  }

  @Test
  public void shouldReturnFailureForBadCredentials() throws Exception {
    userStore.addUser(User.create("petter", "super-encrypted"));

    RequestContext requestContext = loginAs("petter", "petter");

    assertThat(login.authenticate(requestContext), hasPayload(is("FAILURE")));
  }

  @Test
  public void shouldReturnFailureForMissingUser() throws Exception {
    userStore.addUser(User.create("petter", "super-encrypted"));

    RequestContext requestContext = loginAs("matti", "super-encrypted");

    assertThat(login.authenticate(requestContext), hasPayload(is("FAILURE")));
  }

  @Test
  public void shouldReturnBadRequestForMissingUsername() throws Exception {
    userStore.addUser(User.create("petter", "super-encrypted"));

    RequestContext requestContext = RequestContexts.create(
        Request.forUri(URI_BASE + "password=super-encrypted"),
        client,
        ImmutableMap.<String, String>of());

    assertThat(login.authenticate(requestContext), hasStatus(withCode(400)));
  }

  @Test
  public void shouldReturnBadRequestForMissingPassword() throws Exception {
    userStore.addUser(User.create("petter", "super-encrypted"));

    RequestContext requestContext = RequestContexts.create(
        Request.forUri(URI_BASE + "userName=petter"),
        client,
        ImmutableMap.<String, String>of());

    assertThat(login.authenticate(requestContext), hasStatus(withCode(400)));
  }

  private RequestContext loginAs(String userName, String password) {
    Request request = Request.forUri(String.format(URI_BASE + "userName=%s&password=%s", userName, password));
    return RequestContexts.create(request, client, ImmutableMap.of());
  }
}
