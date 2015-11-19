package com.pettermahlen.login;

import com.spotify.apollo.Response;

import cucumber.api.java.en.Given;
import okio.ByteString;

import static com.pettermahlen.login.CommonUtilities.encrypt;
import static com.pettermahlen.login.CommonUtilities.jsonRepresentation;

public class UserStoreStepdefs {
  @Given("^a user with name \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void a_user_with_name_and_password(String userName, String cleartextPassword) throws Throwable {
    String jsonUserData = jsonRepresentation(userName, encrypt(cleartextPassword));

    World.stubClient()
        .respond(Response.forPayload(ByteString.encodeUtf8(jsonUserData)))
        .to("http://userstore/v1/users/" + userName);
  }
}
