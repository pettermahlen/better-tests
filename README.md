# better-tests

This is a small sample project illustrating some techniques I think are useful when writing
tests. It has the following stuff:

1. An illustration of using [Cucumber for acceptance testing](/src/test/resources/com/pettermahlen/login/login.feature).
1. Many examples of small, focused unit test cases that validate a single feature.
1. An example of validating logging, for when logging a condition is considered important enough to mandate a test.
1. Several examples of stubbing (using the [Apollo StubClient](https://github.com/pettermahlen/apollo/blob/master/apollo-test/src/main/java/com/pettermahlen/apollo/test/StubClient.java))
1. An example of using a [fake rather than a mock](/src/test/java/com/pettermahlen/login/FakeUserStore.java)
1. An example of [using a mock](/src/test/java/com/pettermahlen/login/LoginTest.java).
