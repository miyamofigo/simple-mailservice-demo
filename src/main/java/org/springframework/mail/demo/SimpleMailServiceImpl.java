package org.springframework.mail.demo;

import java.util.Properties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.google.inject.Inject;
import com.miyamofigo.java8.nursery.Result;

public final class SimpleMailServiceImpl implements SimpleMailService {

  private final JavaMailSender sender;
  private final SimpleMailMessage templateMessage;

  @Inject
  SimpleMailServiceImpl(JavaMailSender sender, SimpleMailMessage templateMessage) { 
    this.sender = sender; 
    this.templateMessage = templateMessage;
  } 

  public Result<Integer,String> send(SimpleMailMessage simpleMessage) {
    try {
      sender.send(simpleMessage);
    }
    catch (MailException e) {
      return Result.err(e.getClass().getName() + ": " + e.getMessage());
    }
    return Result.ok(1);
  }   

  public Result<Integer,String> send(SimpleMailMessage... simpleMessages) {
    try {
      sender.send(simpleMessages);
    }
    catch (MailException e) {
      return Result.err(e.getClass().getName() + ": " + e.getMessage());
    }
    return Result.ok(simpleMessages.length);
  }

  public JavaMailSender getSender() { return sender; }

  public SimpleMailMessage create(String to) { return create(new String[] {to}); }

  public SimpleMailMessage create(String[] to) {
    SimpleMailMessage message = new SimpleMailMessage(templateMessage); message.setTo(to);  
    return message;
  }
}
