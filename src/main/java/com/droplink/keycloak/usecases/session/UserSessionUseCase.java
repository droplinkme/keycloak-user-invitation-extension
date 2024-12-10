package com.droplink.keycloak.usecases.session;

import java.util.List;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationProcessor;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.resetcred.ResetCredentialEmail;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationFlowModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.RealmModel;
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.keycloak.sessions.RootAuthenticationSessionModel;

public class UserSessionUseCase {
    public static UserSessionOutput exec(UserSessionInput input) {
        ClientModel client = getClient(input.realm);
        AuthenticationFlowModel flow = getAuthenticationFlow(input.realm);
        AuthenticationExecutionModel execution = getAuthenticationExecution(input.realm, flow.getId());
        
        Authenticator authenticator = getAuthenticator();
        configureAuthenticator(input, authenticator);

        AuthenticationSessionModel authSession = createAuthenticationSession(input, client);
        AuthenticationFlowContext context = createAuthenticationContext(input, flow, client, execution, authenticator, authSession);

        return new UserSessionOutput(client, context);
    }

    private static ClientModel getClient(RealmModel realm) {
        return realm.getClientByClientId("account-console");
    }

    private static AuthenticationFlowModel getAuthenticationFlow(RealmModel realm) {
        return realm.getFlowByAlias("reset credentials");
    }

    private static AuthenticationExecutionModel getAuthenticationExecution(RealmModel realm, String flowId) {
        return realm.getAuthenticationExecutionsStream(flowId)
            .filter(execution -> "reset-password".equals(execution.getAuthenticator()))
            .findFirst()
            .orElse(null);
    }

    private static Authenticator getAuthenticator() {
        return new ResetCredentialEmail();
    }

    private static void configureAuthenticator(UserSessionInput input, Authenticator authenticator) {
        authenticator.configuredFor(input.session, input.realm, input.user);
        authenticator.areRequiredActionsEnabled(input.session, input.realm);
    }

    private static AuthenticationSessionModel createAuthenticationSession(UserSessionInput input, ClientModel client) {
        RootAuthenticationSessionModel rootSession = input.session.authenticationSessions().createRootAuthenticationSession(input.realm);
        AuthenticationSessionModel authSession = rootSession.createAuthenticationSession(client);
        authSession.setAuthenticatedUser(input.user);
        authSession.setProtocol(OIDCLoginProtocol.LOGIN_PROTOCOL);
        authSession.setAction(AuthenticationSessionModel.Action.AUTHENTICATE.name());
        return authSession;
    }

    private static AuthenticationProcessor.Result createAuthenticationContext(UserSessionInput input, AuthenticationFlowModel flow, ClientModel client, AuthenticationExecutionModel execution, Authenticator authenticator, AuthenticationSessionModel authSession) {
        AuthenticationProcessor processor = new AuthenticationProcessor();
        processor.setRealm(input.realm);
        processor.setSession(input.session);
        processor.setAuthenticationSession(authSession);
        processor.setAutheticatedUser(input.user);
        processor.setFlowId(flow.getId());
        processor.setClient(client);
        processor.setUriInfo(input.session.getContext().getUri());

        List<AuthenticationExecutionModel> currentExecutions = List.of(execution);
        return processor.createAuthenticatorContext(execution, authenticator, currentExecutions);
    }
}
