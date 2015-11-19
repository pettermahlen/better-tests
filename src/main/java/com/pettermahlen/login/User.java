package com.pettermahlen.login;


import com.google.auto.value.AutoValue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@AutoValue
public abstract class User {
  abstract String userName();
  abstract String password();

  @JsonCreator
  public static User create(@JsonProperty("userName") String userName,
                            @JsonProperty("password") String password) {
    return new AutoValue_User(userName, password);
  }
}
