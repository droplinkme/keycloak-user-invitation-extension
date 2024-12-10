package com.droplink.keycloak.extensions.actions.providers;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;

public class UserInvitationRequiredActionProvider implements RequiredActionProvider {
    
    private final KeycloakSession session;

    public UserInvitationRequiredActionProvider(KeycloakSession session){
      this.session = session;
    }
    
    @Override
    public void evaluateTriggers(RequiredActionContext context) {
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
    }

    @Override
    public void processAction(RequiredActionContext context) {
    }

    @Override
    public void close() {}
}
