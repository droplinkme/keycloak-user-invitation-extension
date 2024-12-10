package com.droplink.keycloak.services;

import org.keycloak.email.EmailException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.droplink.keycloak.usecases.email.SendEmailUserInvitationInput;
import com.droplink.keycloak.usecases.email.SendEmailUserInvitationUseCase;
import com.droplink.keycloak.usecases.link.ResetPasswordLinkInput;
import com.droplink.keycloak.usecases.link.ResetPasswordLinkOutput;
import com.droplink.keycloak.usecases.link.ResetPasswordLinkUseCase;
import com.droplink.keycloak.usecases.session.UserSessionInput;
import com.droplink.keycloak.usecases.session.UserSessionOutput;
import com.droplink.keycloak.usecases.session.UserSessionUseCase;



public class UserInvitationService {
  public static void exec(KeycloakSession session, RealmModel realm, UserModel user) throws EmailException{
    UserSessionOutput userSession = UserSessionUseCase.exec(new UserSessionInput(session, realm, user));
    
    ResetPasswordLinkOutput resetPasswordLink = ResetPasswordLinkUseCase.exec(new ResetPasswordLinkInput(
      session, 
      realm, 
      user,
      userSession.client,
      userSession.context
    ));
    
    SendEmailUserInvitationUseCase.exec(new SendEmailUserInvitationInput(
      session, 
      realm, 
      user,
      resetPasswordLink.link, 
      resetPasswordLink.validityInSecs
    ));

  }
}
