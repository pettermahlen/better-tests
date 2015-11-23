package com.pettermahlen.login;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

/**
 * Runs the cucumber tests defined in {@code login.feature}- the failsafe Maven plugin has been
 * configured to run all tests ending in IT in the integration-test phase.
 */
@RunWith(Cucumber.class)
public class AcceptanceIT {

}
