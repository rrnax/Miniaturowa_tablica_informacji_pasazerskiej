package dwr.MiniaturowaTablica.api.services;

import com.google.gson.Gson;
import dwr.MiniaturowaTablica.api.models.EmailConfirmationToken;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.repository.EmailConfirmationRepository;
import dwr.MiniaturowaTablica.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EmailConfirmationService {

   @Autowired
   private JavaMailSender javaMailSender;
   @Autowired
   private EmailConfirmationRepository emailConfirmationRepository;
   @Autowired
   private UserRepository userRepository;
   @Value("${frontend.url}")
   private String frontendUrl;
   @Autowired
   private Gson gson;


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

   public ResponseEntity<?> confirmEmail(String confirmationToken) {

      EmailConfirmationToken token = emailConfirmationRepository.findByConfirmationToken(confirmationToken);

      if (token != null) {
         User user = token.getUser();
         user.setActive(true);
         userRepository.save(user);
         emailConfirmationRepository.delete(token);
         return ResponseEntity.ok(gson.toJson("Email verified successfully!"));
      }
      return ResponseEntity.badRequest().body(gson.toJson("Couldn't verify email"));
   }


}
