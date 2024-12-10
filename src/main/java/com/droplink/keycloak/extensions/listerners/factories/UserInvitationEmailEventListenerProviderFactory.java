package com.droplink.keycloak.extensions.listerners.factories;

import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import com.droplink.keycloak.extensions.listerners.providers.UserInvitationEmailEventListenerProvider;

public class UserInvitationEmailEventListenerProviderFactory implements EventListenerProviderFactory {

    private static final String PROVIDER_ID = "user-registration-invitation-event";

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new UserInvitationEmailEventListenerProvider(session);
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
