package com.spotify.login;

import com.google.common.base.Throwables;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.apollo.Client;
import com.spotify.apollo.Request;
import com.spotify.apollo.Response;
import com.spotify.apollo.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import okio.ByteString;

/**
 * TODO: document!
 */
public class RemoteUserStore implements UserStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoteUserStore.class);
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private final Client client;

  public RemoteUserStore(Client client) {
    this.client = client;
  }

  @Override
  public Optional<User> findByName(String userName) {
    CompletionStage<Response<ByteString>> stage =
        client.send(Request.forUri("http://userstore/v1/users/" + userName));

    try {
      Response<ByteString> response = stage.toCompletableFuture().get();

      if (response.status().code() == Status.NOT_FOUND.code()) {
        return Optional.empty();
      } else if (response.status().code() == Status.OK.code()) {
        return Optional.of(OBJECT_MAPPER.readValue(response.payload().get().utf8(), User.class));
      }

      LOGGER.warn("Remote store returned response {} - treating as not found", response);
      return Optional.empty();
    } catch (InterruptedException | ExecutionException | IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
