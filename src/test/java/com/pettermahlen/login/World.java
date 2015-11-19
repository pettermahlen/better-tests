package com.pettermahlen.login;

import com.spotify.apollo.test.ServiceHelper;
import com.spotify.apollo.test.StubClient;

public class World {
  private static ServiceHelper serviceHelper;
  private static StubClient stubClient;

  public static StubClient stubClient() throws Exception {
    maybeInitHelperAndClient();

    return stubClient;
  }

  public static ServiceHelper serviceHelper() throws Exception {
    maybeInitHelperAndClient();

    return serviceHelper;
  }

  private static synchronized void maybeInitHelperAndClient() throws InterruptedException {
    if (stubClient == null) {
      serviceHelper = ServiceHelper.create(LoginSetup::init, ServiceRunner.SERVICE_NAME);

      serviceHelper.start();

      stubClient = serviceHelper.stubClient();
    }
  }
}
