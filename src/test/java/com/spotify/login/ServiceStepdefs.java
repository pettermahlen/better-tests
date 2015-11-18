package com.spotify.login;

import com.spotify.apollo.Request;
import com.spotify.apollo.Response;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okio.ByteString;

import static com.spotify.apollo.test.unit.ResponseMatchers.hasPayload;
import static com.spotify.apollo.test.unit.ResponseMatchers.hasStatus;
import static com.spotify.apollo.test.unit.StatusTypeMatchers.withCode;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class ServiceStepdefs {

  Response<ByteString> response;

  @When("^a login attempt is made with \"([^\"]*)\" and \"([^\"]*)\"$")
  public void a_login_attempt_is_made_with_and(String userName, String cleartextPassword) throws Throwable {
    final String uri = String.format("/login?user=%s&password=%s", userName, encrypt(cleartextPassword));
    response = World.serviceHelper()
        .request(Request.forUri(uri))
        .toCompletableFuture().get();
  }

  @Then("^the result is \"([^\"]*)\"$")
  public void the_result_is(String result) throws Throwable {
    assertThat(response, hasStatus(withCode(200)));
    assertThat(response, hasPayload(withString(result)));
  }

  private Matcher<ByteString> withString(String result) {
    return new TypeSafeMatcher<ByteString>() {
      @Override
      protected boolean matchesSafely(ByteString item) {
        return item.utf8().equals(result);
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("bytestring whose utf8 value matches '" + result + "'");
      }

      @Override
      protected void describeMismatchSafely(ByteString item, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendText(item.utf8());
      }
    };
  }

  private String encrypt(String cleartextPassword) {
    return "crypto!" + cleartextPassword;
  }
}
