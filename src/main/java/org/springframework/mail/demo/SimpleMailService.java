package org.springframework.mail.demo;

import java.io.InputStream;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import com.miyamofigo.java8.nursery.Result;

public interface SimpleMailService {

  Result<Integer,String> send(SimpleMailMessage simpleMessage);

  Result<Integer,String> send(SimpleMailMessage... simpleMessages);

  SimpleMailMessage create(String to);

  SimpleMailMessage create(String[] to);  
}
