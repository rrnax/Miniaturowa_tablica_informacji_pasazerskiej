package dwr.MiniaturowaTablica.api.services;

import dwr.MiniaturowaTablica.api.models.ERole;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.payload.request.LoginRequest;
import dwr.MiniaturowaTablica.api.payload.request.SignupRequest;
import dwr.MiniaturowaTablica.api.repository.UserRepository;
import dwr.MiniaturowaTablica.api.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    public User updateUserEmail(String newEmail, String jwt){
        if (userRepository.existsByEmail(newEmail)) throw new RuntimeException("Email already taken");

        User user = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(jwt))
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    public User updatedPassword(String newPassword, String jwt){
        User user = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(jwt))
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        user.setPassword(encoder.encode(newPassword));
        return userRepository.save(user);
    }

    public void deleteUser(String jwt){
        User user = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(jwt))
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        userRepository.delete(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findUserByName(String userName){
        return userRepository.findByUsername(userName).orElseThrow(()-> new RuntimeException("User Not Found"));
    }


}