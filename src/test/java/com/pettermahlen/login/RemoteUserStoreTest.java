package com.pettermahlen.login;

import com.spotify.apollo.Request;
import com.spotify.apollo.Response;
import com.spotify.apollo.Status;
import com.spotify.apollo.test.StubClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.helpers.MessageFormatter;

import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.List;
import java.util.Optional;

import okio.ByteString;

import static com.spotify.apollo.test.unit.RequestMatchers.uri;
import static com.pettermahlen.login.CommonUtilities.jsonRepresentation;
import static java.util.stream.Collectors.toList;
import static okio.ByteString.encodeUtf8;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RemoteUserStoreTest {
  RemoteUserStore store;

  StubClient client;

  TestLogger testLogger;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    testLogger = TestLoggerFactory.getTestLogger(RemoteUserStore.class);
    testLogger.clearAll();

    client = new StubClient();

    store = new RemoteUserStore(client);
  }

  @Test
  public void shouldConvertRemoteResponseToUser() throws Exception {
    client
        .respond(Response.forPayload(encodeUtf8(jsonRepresentation("matti", "pwd"))))
        .to(uri(endsWith("matti")));

    assertThat(store.findByName("matti"), equalTo(Optional.of(User.create("matti", "pwd"))));
  }

  @Test
  public void shouldConvert404ToEmpty() throws Exception {
    client
        .respond(Response.forStatus(Status.NOT_FOUND))
        .to(uri(endsWith("landen")));

    assertThat(store.findByName("landen"), is(Optional.empty()));
  }

  @Test
  public void shouldReturnEmptyForOtherCodes() throws Exception {
    client
        .respond(Response.forStatus(Status.IM_A_TEAPOT))
        .to(any(Request.class));

    assertThat(store.findByName("teapot?"), is(Optional.empty()));
  }

  @Test
  public void shouldLogOtherCodes() throws Exception {
    client
        .respond(Response.forStatus(Status.IM_A_TEAPOT))
        .to(any(Request.class));

    store.findByName("teapot?");

    List<LoggingEvent> loggingEvents = testLogger.getAllLoggingEvents().stream()
        .filter(loggingEvent -> loggingEvent.getLevel() == Level.WARN)
        .collect(toList());
    assertThat(loggingEvents.size(), is(1));
    assertThat(asFormattedMessage(loggingEvents.get(0)),
               containsString(Status.IM_A_TEAPOT.toString()));
  }

  @Test
  public void shouldPropagateExceptions() throws Exception {
    client
        .respond(Response.forPayload(ByteString.encodeUtf8("bad json")))
        .to(any(Request.class));

    thrown.expectMessage("bad json");
    store.findByName("random-user");
  }

  private String asFormattedMessage(LoggingEvent loggingEvent) {
    return MessageFormatter.arrayFormat(
        loggingEvent.getMessage(),
        loggingEvent.getArguments().toArray()
    ).getMessage();
  }
}
