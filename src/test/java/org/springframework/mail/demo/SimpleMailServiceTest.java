package org.springframework.mail.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import org.springframework.mail.SimpleMailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class SimpleMailServiceTest {

  private Logger logger;

  @Before
  public void setUp() {
    logger = LoggerFactory.getLogger(SimpleMailServiceTest.class);
    logger.info("Logger is set.");
  } 

  @Test 
  public void runTest() {
    SimpleMailService service = MailServiceBootstrap.createMailServiceFactory().create();
    SimpleMailMessage message = service.create("miyamofigo@i.softbank.jp");
    assert(service.send(message).isOk()); 
  }
}

