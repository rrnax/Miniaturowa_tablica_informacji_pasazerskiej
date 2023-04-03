package dwr.MiniaturowaTablica.api.services;

import dwr.MiniaturowaTablica.api.models.BlockedJwt;
import dwr.MiniaturowaTablica.api.models.ERole;
import dwr.MiniaturowaTablica.api.models.Role;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.payload.request.LoginRequest;
import dwr.MiniaturowaTablica.api.payload.request.SignupRequest;
import dwr.MiniaturowaTablica.api.payload.response.JwtResponse;
import dwr.MiniaturowaTablica.api.repository.BlockedJwtRepository;
import dwr.MiniaturowaTablica.api.repository.RoleRepository;
import dwr.MiniaturowaTablica.api.repository.UserRepository;
import dwr.MiniaturowaTablica.api.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

   private final AuthenticationManager authenticationManager;

   private final JwtUtils jwtUtils;

   private final UserRepository userRepository;

   private final PasswordEncoder passwordEncoder;

   private final RoleRepository roleRepository;

   private final EmailConfirmationService emailConfirmationService;

   private final BlockedJwtRepository blockedJwtRepository;

   public JwtResponse singIn(LoginRequest loginRequest, HttpServletResponse response){
      Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      User userDetails = (User) authentication.getPrincipal();

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

      response.addHeader("Authorization","Bearer "+jwt);

      return new JwtResponse(userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles);
   }

   public String singup(SignupRequest signUpRequest){
      if (userRepository.existsByUsername(signUpRequest.getUsername())) {
         throw new RuntimeException("Error: Username is already taken!");
      }

      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
         throw new RuntimeException("Error: Email is already in use!");
      }

      // Create new user's account
      User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            passwordEncoder.encode(signUpRequest.getPassword()));

      Set<String> strRoles = signUpRequest.getRoles();
      Set<Role> roles = new HashSet<>();

      if (strRoles == null) {
         Role userRole = roleRepository.findByName(ERole.ROLE_USER)
               .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
         roles.add(userRole);
      } else {
         strRoles.forEach(role -> {
            switch (role) {
               case "admin":
                  Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                  roles.add(adminRole);

                  break;
               case "mod":
                  Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                  roles.add(modRole);

                  break;
               default:
                  Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                  roles.add(userRole);
            }
         });
      }

      user.setRoles(roles);
      userRepository.save(user);
      emailConfirmationService.sendEmail(user);

      return "User registered successfully!";
   }

   public String logout(String token){
      try {
         blockedJwtRepository.save(new BlockedJwt(null, token));
      }catch (Exception e){
         throw new RuntimeException("Error: Couldn't logout");
      }
      clearOldJwts();
      return "Successfully logged out";
   }

   @Async
   public void clearOldJwts(){
      List<BlockedJwt> blockedJwts = blockedJwtRepository.findAll();
      Set<BlockedJwt> expiredTokens =  blockedJwts.stream()
            .filter( it -> !jwtUtils.validateJwtToken(it.getToken()) )
            .collect(Collectors.toSet());
      blockedJwtRepository.deleteAll(expiredTokens);
   }
}
