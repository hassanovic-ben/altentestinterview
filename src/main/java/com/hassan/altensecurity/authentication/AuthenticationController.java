package com.hassan.altensecurity.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/account")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse register = authenticationService.register(registerRequest);
        return register != null ? ResponseEntity.ok("User " + registerRequest.getEmail() + " has been registered successfully") : new ResponseEntity<>("can not add admin user", HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponse> connect(@RequestBody AuthenticateRequest authenticateRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));
    }
}
