package com.droplink.keycloak.usecases.email;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class SendEmailUserInvitationInput {
  public KeycloakSession session; 
  public RealmModel realm; 
  public UserModel user;
  public String link; 
  public int validityInSecs;

  public SendEmailUserInvitationInput(
    KeycloakSession session, 
    RealmModel realm, 
    UserModel user,
    String link,
    int validityInSecs
  ){
    this.session = session;
    this.realm = realm;
    this.user = user;
    this.link = link;
    this.validityInSecs = validityInSecs;
  }
}
