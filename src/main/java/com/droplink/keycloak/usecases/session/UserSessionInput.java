package com.droplink.keycloak.usecases.session;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class UserSessionInput {
  public KeycloakSession session; 
  public RealmModel realm; 
  public UserModel user;

  public UserSessionInput(KeycloakSession session, RealmModel realm, UserModel user){
    this.session = session;
    this.realm = realm;
    this.user = user;
  }
}
