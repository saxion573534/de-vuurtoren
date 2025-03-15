package org.saxion.devuurtoren.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginScreenControllerTest {

    LoginScreenController loginScreenController = new LoginScreenController();

    @Test
    void testLoginWithCorrectCredentials() {
        String user = "@org.vt";
        String password = "vuurtoren@administrator";

        boolean result = loginScreenController.login(user, password);

        assertTrue(result, "Login should succeed with correct credentials.");
    }

    @Test
    void testLoginWithIncorrectCredentials() {
        String user = "@org.vt";
        String password = "wrongPassword";

        boolean result = loginScreenController.login(user, password);

        assertFalse(result, "Login should fail with incorrect credentials.");
    }

    @Test
    void testLoginWithDifferentCaseCredentials() {
        String user = "@ORG.VT";
        String password = "VuurToren@Administrator";

        boolean result = loginScreenController.login(user, password);

        assertFalse(result, "Login should not succeed with different case credentials.");
    }

}