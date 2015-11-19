package com.spotify.login;

import com.spotify.apollo.Request;
import com.spotify.apollo.Response;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okio.ByteString;

import static com.spotify.apollo.test.unit.ResponseMatchers.hasPayload;
import static com.spotify.apollo.test.unit.ResponseMatchers.hasStatus;
import static com.spotify.apollo.test.unit.StatusTypeMatchers.withCode;
import static com.spotify.login.CommonUtilities.encrypt;
import static com.spotify.login.CommonUtilities.withString;
import static org.junit.Assert.assertThat;


public class ServiceStepdefs {

  Response<ByteString> response;

  @When("^a login attempt is made with \"([^\"]*)\" and \"([^\"]*)\"$")
  public void a_login_attempt_is_made_with_and(String userName, String cleartextPassword) throws Throwable {
    final String uri = String.format(
        "/login?userName=%s&password=%s",
        userName,
        encrypt(cleartextPassword));

    response = World.serviceHelper()
        .request(Request.forUri(uri))
        .toCompletableFuture().get();
  }

  @Then("^the result is \"([^\"]*)\"$")
  public void the_result_is(String result) throws Throwable {
    assertThat(response, hasStatus(withCode(200)));
    assertThat(response, hasPayload(withString(result)));
  }

}
