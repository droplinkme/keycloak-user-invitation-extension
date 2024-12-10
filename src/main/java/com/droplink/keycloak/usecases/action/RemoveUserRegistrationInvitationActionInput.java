package com.droplink.keycloak.usecases.action;

import org.keycloak.models.KeycloakSession;

public class RemoveUserRegistrationInvitationActionInput {
    public KeycloakSession session;
    public String userId;
  
  public RemoveUserRegistrationInvitationActionInput(
    KeycloakSession session,
    String userId
  ){
    this.session = session;
    this.userId = userId;
  }
}
