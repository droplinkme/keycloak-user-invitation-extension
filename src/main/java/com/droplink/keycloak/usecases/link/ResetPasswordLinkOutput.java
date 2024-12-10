package com.droplink.keycloak.usecases.link;

public class ResetPasswordLinkOutput {
  public String link; 
  public int validityInSecs;

  public ResetPasswordLinkOutput(
    String link,
    int validityInSecs
  ){
    this.link = link;
    this.validityInSecs = validityInSecs;
  }
}
