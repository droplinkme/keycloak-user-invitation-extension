package com.droplink.keycloak.usecases.email;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.keycloak.email.EmailException;
import org.keycloak.email.EmailTemplateProvider;

public class SendEmailUserInvitationUseCase {
  private static final String TEMPLATE_EMAIL = "email-user-invitation.ftl";

  public static void exec(SendEmailUserInvitationInput input) throws EmailException{
      EmailTemplateProvider emailTemplateProvider = input.session.getProvider(EmailTemplateProvider.class);
      emailTemplateProvider.setRealm(input.realm).setUser(input.user);
      long expirationInMinutes = TimeUnit.SECONDS.toMinutes(input.validityInSecs);
      Map<String, Object> attributes = new HashMap<>();
      
      attributes.put("user", input.user);
      attributes.put("link", input.link);
      attributes.put("expirationInMinutes", expirationInMinutes);

      try {
          emailTemplateProvider.send("Bem-vindo à Suno: Crie sua senha para acessar", TEMPLATE_EMAIL, attributes);
      } catch (EmailException e) {
          e.printStackTrace();
          throw e;
      }
  }
}
