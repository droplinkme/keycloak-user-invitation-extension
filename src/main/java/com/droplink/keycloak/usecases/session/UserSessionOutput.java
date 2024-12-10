package com.droplink.keycloak.usecases.session;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.models.ClientModel;

public class UserSessionOutput {
  public ClientModel client;
  public AuthenticationFlowContext context;

  public UserSessionOutput(
    ClientModel client,
    AuthenticationFlowContext context
  ){
    this.client = client;
    this.context = context;
  }
}
