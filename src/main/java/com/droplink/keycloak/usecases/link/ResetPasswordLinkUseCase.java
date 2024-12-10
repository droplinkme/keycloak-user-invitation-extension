package com.droplink.keycloak.usecases.link;

import org.keycloak.authentication.actiontoken.resetcred.ResetCredentialsActionToken;
import org.keycloak.common.util.Time;
import org.keycloak.sessions.AuthenticationSessionCompoundId;

import jakarta.ws.rs.core.UriBuilder;

public class ResetPasswordLinkUseCase {
    public static ResetPasswordLinkOutput exec(ResetPasswordLinkInput input) {
        int validityInSecs = input.realm.getActionTokenGeneratedByUserLifespan(ResetCredentialsActionToken.TOKEN_TYPE);
        int absoluteExpirationInSecs = Time.currentTime() + validityInSecs;

        String authSessionEncodedId = AuthenticationSessionCompoundId.fromAuthSession(input.context.getAuthenticationSession()).getEncodedId();

        ResetCredentialsActionToken token = new ResetCredentialsActionToken(
            input.user.getId(),
            input.user.getEmail(),
            absoluteExpirationInSecs,
            authSessionEncodedId,
            input.client.getClientId()
        );

        String link = UriBuilder.fromUri(
          input.context.getActionTokenUrl(
            token.serialize(input.session, input.realm, input.session.getContext().getUri())
          )
        ).build().toString();

        return new ResetPasswordLinkOutput(link, validityInSecs);
    }
}
