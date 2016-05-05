package org.springframework.mail.demo;

public class MailServiceBootstrap {

  private static final String MAIL_PROPERTIES_FILE = "mail.properties";

  public static SimpleMailServiceFactory createMailServiceFactory() {
    return SimpleMailServiceFactoryImpl.createFactory(MAIL_PROPERTIES_FILE);
  }
}
