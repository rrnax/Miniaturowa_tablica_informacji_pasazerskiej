package dwr.MiniaturowaTablica.api.controllers;

import com.google.gson.Gson;
import dwr.MiniaturowaTablica.api.assemblers.UserAssembler;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.payload.request.*;
import dwr.MiniaturowaTablica.api.payload.response.AppStyleResponse;
import dwr.MiniaturowaTablica.api.payload.response.UserResponse;
import dwr.MiniaturowaTablica.api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UsersController {

   private final UserService userService;
   private final Gson gson;
   private final UserAssembler userAssembler;

   @PutMapping("/all/update/email")
   public ResponseEntity<UserResponse> updatedEmail(
         @RequestBody @Valid NewEmailRequest newEmailRequest,
         @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt
   ) {
      User user = userService.updateUserEmail(newEmailRequest.getNewEmail(), jwt);
      return ResponseEntity.ok(userAssembler.toUserResponse(user));
   }

   @PutMapping("/all/update/password")
   public ResponseEntity<UserResponse> updatePassword(
         @RequestBody @Valid NewPasswordRequest newPasswordRequest,
         @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt
   ) {
      User user = userService.updatePassword(newPasswordRequest.getNewPassword(), jwt);
      return ResponseEntity.ok(userAssembler.toUserResponse(user));
   }

   @PutMapping("/all/update/username")
   public ResponseEntity<UserResponse> updateUserName(
         @RequestBody @Valid NewUserNameRequest newUserNameRequest,
         @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt
   ) {
      User user = userService.updateUserName(newUserNameRequest.getNewUsername(), jwt);
      return ResponseEntity.ok(userAssembler.toUserResponse(user));
   }

   @DeleteMapping("/all/delete/user")
   public ResponseEntity<?> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
      userService.deleteUser(jwt);
      return ResponseEntity.ok(null);
   }

   @GetMapping("/all/get/app/style")
   public ResponseEntity<?> getAppStyle(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
      String appStyle = userService.getAppStyle(jwt);
      return ResponseEntity.ok(new AppStyleResponse(appStyle));
   }

   @PutMapping("/all/update/app/style")
   public ResponseEntity<?> updateAppStyle(
         @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
         @RequestBody @Valid NewAppStyleRequest request
   ) {
      String appStyle = userService.updateAppStyle(jwt, request);
      return ResponseEntity.ok(appStyle);
   }

   @GetMapping("/admin/get/allUsers")
   public ResponseEntity<List<User>> allUser() {
      List<User> users = userService.getAllUsers();
      return ResponseEntity.ok(users);
   }

   @GetMapping("/admin/get/user/by/username")
   public ResponseEntity<User> getUserByUsername(@RequestBody UsernameRequest usernameRequest) {
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
