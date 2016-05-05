package org.springframework.mail.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.name.Named;

public final class SimpleMailServiceFactoryImpl implements SimpleMailServiceFactory {

  private final String host;
  private final String port; 
  private final String username; 
  private final String password;

  private final Properties javaMailProperties;  
  private final SimpleMailMessage templateMessage;

  @Inject
  private SimpleMailServiceFactoryImpl(
    @Named("host") String host, @Named("port") String port, @Named("username") String username,
    @Named("password") String password, 
    @Named("message.from") String from, @Named("message.text") String text,
    @Named("message.subject") String subject,
    @Named("mail.smtp.auth") String auth, @Named("mail.smtp.starttls.enable") String enable, 
    @Named("mail.smtp.quitwait") String quitwait 
  ) {

    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;

    SimpleMailMessage message= new SimpleMailMessage();
    message.setFrom(from);
    message.setText(text);
    message.setSubject(subject);
    this.templateMessage = message;

    Properties props = new Properties();
    props.setProperty("mail.smtp.auth", auth);  
    props.setProperty("mail.smtp.starttls.enable", enable); 
    props.setProperty("mail.smtp.quitwait", quitwait);  
    this.javaMailProperties = props;
  }
 
  public static SimpleMailServiceFactory createFactory(String filename) {

    Injector injector = Guice.createInjector(
                          new AbstractModule() {
                            @Override
                            protected void configure() {
                              Properties properties = new Properties();

                              try {
                                URI uri = ClassLoader.getSystemClassLoader()
                                                     .getResource(filename)
                                                     .toURI();
                                properties.load(new FileInputStream(new File(uri)));
                              }
                              catch (IOException|URISyntaxException e) { 
                                e.printStackTrace(); System.exit(1); 
                              }

                              Names.bindProperties(binder(), properties);
                            }
                          }
                        );

    return (SimpleMailServiceFactory) injector.getInstance(SimpleMailServiceFactoryImpl.class);
  }

  public SimpleMailService create() {

    Injector injector = Guice.createInjector(new AbstractModule() {
                          @Override
                          protected void configure() {
                            bind(JavaMailSender.class).to(JavaMailSenderImpl.class);  
                            bind(SimpleMailMessage.class).toInstance(templateMessage);
                          }
                        });
    
    SimpleMailServiceImpl service = injector.getInstance(SimpleMailServiceImpl.class); 

    JavaMailSenderImpl sender = (JavaMailSenderImpl) service.getSender();
    sender.setHost(host);
    sender.setPort(Integer.parseInt(port));
    sender.setUsername(username);
    sender.setPassword(password);
    sender.setJavaMailProperties(javaMailProperties);

    return (SimpleMailService) service;
  }
}
