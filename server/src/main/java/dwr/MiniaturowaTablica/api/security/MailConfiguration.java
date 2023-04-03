package dwr.MiniaturowaTablica.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

   @Value("${mail.user}")
   private String mailUser;

   @Value("${mail.password}")
   private String mailPassword;

   @Bean
   public JavaMailSender getJavaMailSender() {
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
      mailSender.setHost("smtp.gmail.com");
      mailSender.setPort(587);

      mailSender.setUsername(mailUser);
      mailSender.setPassword(mailPassword);

      Properties props = mailSender.getJavaMailProperties();
      props.put("mail.transport.protocol", "smtp");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");

      return mailSender;
   }
}