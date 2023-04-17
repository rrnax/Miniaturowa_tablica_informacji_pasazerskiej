package dwr.MiniaturowaTablica.api.controllers;

import com.google.gson.Gson;
import dwr.MiniaturowaTablica.api.models.ERole;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.payload.request.LoginRequest;
import dwr.MiniaturowaTablica.api.payload.request.RoleRequest;
import dwr.MiniaturowaTablica.api.payload.request.SignupRequest;
import dwr.MiniaturowaTablica.api.payload.request.UsernameRequest;
import dwr.MiniaturowaTablica.api.repository.UserRepository;
import dwr.MiniaturowaTablica.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UsersController {

    private final UserService userService;
    private final Gson gson;

    @PostMapping("/all/updateEmail")
    public ResponseEntity<User> updatedEmail(@RequestBody String newEmail, @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt){
        User user = userService.updateUserEmail(newEmail,jwt);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/all/updatePassword")
    public ResponseEntity<User> updatedPassword(@RequestBody String newPassword, @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt){
        User user = userService.updatedPassword(newPassword, jwt);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/all/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt){
        userService.deleteUser(jwt);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/admin/allUsers")
    public ResponseEntity<List<User>> allUser(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/admin/getUserByUsername")
    public ResponseEntity<User> getUserByUsername(@RequestBody UsernameRequest usernameRequest){
        User user = userService.findUserByName(usernameRequest.getUsernameToFind());
        return ResponseEntity.ok(user);
    }

    /*@PostMapping("/updateRole")
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
        }*/

    @ExceptionHandler({RuntimeException.class, UsernameNotFoundException.class})
    public ResponseEntity<String> handleException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(gson.toJson(exception.getMessage()));
    }

}
