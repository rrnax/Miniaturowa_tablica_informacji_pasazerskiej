package dwr.MiniaturowaTablica.api.controllers;

import dwr.MiniaturowaTablica.api.models.ERole;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.payload.request.LoginRequest;
import dwr.MiniaturowaTablica.api.payload.request.RoleRequest;
import dwr.MiniaturowaTablica.api.payload.request.SignupRequest;
import dwr.MiniaturowaTablica.api.payload.request.UsernameRequest;
import dwr.MiniaturowaTablica.api.payload.response.MessageResponse;
import dwr.MiniaturowaTablica.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;





/**
 * It is a class to modify and delete users.
 */
@RestController
@RequestMapping("/api/user")
public class UsersController {
    @Autowired
    PasswordEncoder encoder;
    final
    AuthenticationManager authenticationManager;
    final
    UserRepository userRepository;

    public UsersController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    /**
     * Method to changing a user email.
     * @param signUpRequest it is a param to identify and authority user.
     * @return 0 when email was changed and throw exception when password, email or username is wrong.
     */
    @PostMapping("/updateEmail")
    public ResponseEntity<?> updatedEmail(@RequestBody SignupRequest signUpRequest){
        //Authority
         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

        // whether the email is unique
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // find user
        User user = userRepository.findByUsername(signUpRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + signUpRequest.getUsername()));
        // changing email
        user.setEmail(signUpRequest.getEmail());
        userRepository.save(user);

        return ResponseEntity.ok(0 );
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatedPassword(@RequestBody SignupRequest signUpRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

        User user = userRepository.findByUsername(signUpRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + signUpRequest.getUsername()));
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(0 );
    }

    /**
     * Method to delete user.
     * @param loginRequest it is a param to identify and authority user.
     * @return 0 when user is deleted and throw exception when password or username is wrong.
     */
    @PostMapping("/deleteUser")
    //Authority
    public ResponseEntity<?> deleteUser(@RequestBody LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // find user
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));

        //delete user
        userRepository.delete(user);
        return ResponseEntity.ok(0 );
    }

    /**
     * Method to check the list of users for admin
     * @param loginRequest it is a param to identify and authority user.
     * @return List of users when role is Admin or -1 when user role isn't Admin
     */
    @PostMapping("/allUsers")
    //Authority
    public ResponseEntity<?> allUser(@RequestBody LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // find user
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));
        if(user.getName().equals(ERole.ROLE_ADMIN)){
            return ResponseEntity.ok(userRepository.findAll());
        }
        else{
            return ResponseEntity.ok(-1);
        }
    }

    /**
     * Method to find user using username
     * @param usernameRequest (username, password, usernameToFind)
     * @return class User or errors
     */
    @PostMapping("/getUserByUsername")
    //Authority
    public ResponseEntity<?> getUserByUsername(@RequestBody UsernameRequest usernameRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameRequest.getUsername(), usernameRequest.getPassword()));
        // admin?
        User user = userRepository.findByUsername(usernameRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + usernameRequest.getUsername()));
        if(user.getName().equals(ERole.ROLE_ADMIN)){
            //return user
            user = userRepository.findByUsername(usernameRequest.getUsernameToFind())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + usernameRequest.getUsername()));
            user.setPassword("");
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.ok(-1);
    }

    //dla bezpieczeństwa lepiej by zmieniać role ręcznie ale na razie zostawie

    /**
     * Method to change role
     * @param roleRequest (username, password, usernameToRoleChange, Role)
     * @return 0 if role was change and -1 when you don't have permission (you aren't admin)
     */
    @PostMapping("/updateRole")
    public ResponseEntity<?> UpdateRole(@RequestBody RoleRequest roleRequest){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(roleRequest.getUsername(), roleRequest.getPassword()));
            User user = userRepository.findByUsername(roleRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + roleRequest.getUsername()));
            if(user.getName().equals(ERole.ROLE_ADMIN)){
                user = userRepository.findByUsername(roleRequest.getUsernameToRoleChange())
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + roleRequest.getUsername()));
                user.setRoles(roleRequest.getRole());
                return ResponseEntity.ok(0);
            }
            return ResponseEntity.ok(-1);
        }

}
