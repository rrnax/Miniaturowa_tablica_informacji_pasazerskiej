package dwr.MiniaturowaTablica.api.services;

import dwr.MiniaturowaTablica.api.models.EmailConfirmationToken;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.repository.EmailConfirmationRepository;
import dwr.MiniaturowaTablica.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailConfirmationService {

   private final JavaMailSender javaMailSender;

   private final EmailConfirmationRepository emailConfirmationRepository;

   private final UserRepository userRepository;
   @Value("${frontend.url}")
   private String frontendUrl;


   @Async
   public void sendEmail(User user) {
      String token = UUID.randomUUID().toString();
      EmailConfirmationToken confirmationToken = new EmailConfirmationToken(token, LocalDate.now().plusDays(1), user);

      emailConfirmationRepository.save(confirmationToken);

      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(user.getEmail());
      mailMessage.setSubject("Complete Registration!");
      mailMessage.setText("To confirm your account, please click here : "
            + frontendUrl + "api/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());
      javaMailSender.send(mailMessage);
   }

   public String confirmEmail(String confirmationToken) {

      EmailConfirmationToken token = emailConfirmationRepository.findByConfirmationToken(confirmationToken);

      if (token != null) {
         User user = token.getUser();
         user.setActive(true);
         userRepository.save(user);
         emailConfirmationRepository.delete(token);
         return "Email verified successfully!";
      }
      throw new RuntimeException("Couldn't verify email");
   }


}
