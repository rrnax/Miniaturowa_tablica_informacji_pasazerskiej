package dwr.MiniaturowaTablica.api.controllers;
import com.google.gson.Gson;
import dwr.MiniaturowaTablica.api.payload.request.LoginRequest;
import dwr.MiniaturowaTablica.api.payload.request.SignupRequest;
import dwr.MiniaturowaTablica.api.payload.response.JwtResponse;
import dwr.MiniaturowaTablica.api.security.jwt.JwtUtils;
import dwr.MiniaturowaTablica.api.services.AuthService;
import dwr.MiniaturowaTablica.api.services.EmailConfirmationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final EmailConfirmationService emailConfirmationService;
    private final AuthService authService;
    private final Gson gson;


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        JwtResponse result = authService.singIn(loginRequest, response);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        String registerMessage = authService.singup(signUpRequest);
        return ResponseEntity.ok(gson.toJson(registerMessage));
    }

    @GetMapping(value = "/confirm-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        String emailConfirmMessage = emailConfirmationService.confirmEmail(confirmationToken);
        return ResponseEntity.ok(gson.toJson(emailConfirmMessage));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String logoutMessage = authService.logout(jwtUtils.headerToToken(header));
        return ResponseEntity.ok(gson.toJson(logoutMessage));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(gson.toJson(exception.getMessage()));
    }
}