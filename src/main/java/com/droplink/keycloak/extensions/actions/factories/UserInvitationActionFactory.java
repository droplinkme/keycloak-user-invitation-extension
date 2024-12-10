package com.droplink.keycloak.extensions.actions.factories;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import com.droplink.keycloak.extensions.actions.providers.UserInvitationRequiredActionProvider;

public class UserInvitationActionFactory implements RequiredActionFactory {
    public static String ACTION_ID = "USER_REGISTRATION_INVITATION";

    @Override
    public String getDisplayText() {
      return "User Registration Invitation";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new UserInvitationRequiredActionProvider(session);
    }

    @Override
    public void init(Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}

    @Override
    public String getId() {
      return ACTION_ID;
    }

}
