package com.droplink.keycloak.usecases.action;

import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.droplink.keycloak.extensions.actions.factories.UserInvitationActionFactory;

public class RemoveUserRegistrationInvitationActionUseCase {
  public static void exec(RemoveUserRegistrationInvitationActionInput input){
    RealmModel realm = input.session.getContext().getRealm();
    UserModel user = input.session.users().getUserById(realm, input.userId);

    if(user != null){
      user.removeRequiredAction(UserInvitationActionFactory.ACTION_ID);
      user.setEmailVerified(true);
    }
  }
}
