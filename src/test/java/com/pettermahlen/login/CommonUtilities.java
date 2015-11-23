package com.pettermahlen.login;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import okio.ByteString;

class CommonUtilities {

  static Matcher<ByteString> withString(String result) {
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

  static String jsonRepresentation(String userName, String password) {
    return String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, password);
  }

  // super-secret password encryption
  static String encrypt(String cleartextPassword) {
    return "crypto!" + cleartextPassword;
  }
}
