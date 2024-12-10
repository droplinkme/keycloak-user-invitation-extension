package com.droplink.keycloak.extensions.listerners.providers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.keycloak.email.EmailException;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.droplink.keycloak.extensions.actions.factories.UserInvitationActionFactory;
import com.droplink.keycloak.services.UserInvitationService;
import com.droplink.keycloak.usecases.action.RemoveUserRegistrationInvitationActionInput;
import com.droplink.keycloak.usecases.action.RemoveUserRegistrationInvitationActionUseCase;

public class UserInvitationEmailEventListenerProvider implements EventListenerProvider {
    private final KeycloakSession session;

    public UserInvitationEmailEventListenerProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
      List<EventType> actions = Arrays.asList(EventType.UPDATE_PASSWORD);
      if (actions.contains(event.getType())) {
        RemoveUserRegistrationInvitationActionUseCase.exec(
          new RemoveUserRegistrationInvitationActionInput(session, event.getUserId())
        );
      }
    }

    @Override
    public void close() {
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
      List<OperationType> opeatirions = Arrays.asList(OperationType.CREATE, OperationType.UPDATE);
      Boolean availebeOperation = opeatirions.contains(event.getOperationType());
      Boolean availebeResource = event.getResourceTypeAsString().equals(ResourceType.USER.toString());

      if (availebeOperation && availebeResource) {
            String realmId = event.getRealmId();
            String userId = event.getResourcePath().split("/")[1];
            RealmModel realm = session.realms().getRealm(realmId);
            
            boolean isActionEnabled = realm.getRequiredActionProvidersStream()
              .anyMatch(provider -> provider.isEnabled() && UserInvitationActionFactory.ACTION_ID.equals(provider.getProviderId()));

            if(!isActionEnabled){
              RemoveUserRegistrationInvitationActionUseCase.exec(
                new RemoveUserRegistrationInvitationActionInput(session, userId)
              );
              return;
            }
            
            UserModel user = session.users().getUserById(realm, userId);
            Stream<String> requiredActions = user.getRequiredActionsStream();
            if (requiredActions.anyMatch(action -> action.equals(UserInvitationActionFactory.ACTION_ID))) {
                try {
                    UserInvitationService.exec(session, realm, user);
                } catch (EmailException ex) {
                  ex.printStackTrace();
                }
            }
      }
    }
}