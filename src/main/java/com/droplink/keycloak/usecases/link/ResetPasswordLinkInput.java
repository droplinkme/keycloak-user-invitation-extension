package com.droplink.keycloak.usecases.link;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class ResetPasswordLinkInput {
  public KeycloakSession session; 
  public RealmModel realm; 
  public UserModel user;
  public ClientModel client;
  public AuthenticationFlowContext context;

  public ResetPasswordLinkInput(
    KeycloakSession session, 
    RealmModel realm, 
    UserModel user,
    ClientModel client,
    AuthenticationFlowContext context
  ){
    this.session = session;
    this.realm = realm;
    this.user = user;
    this.client = client;
    this.context = context;
  }
}
