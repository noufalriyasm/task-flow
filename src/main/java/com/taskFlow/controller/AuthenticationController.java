package com.taskFlow.controller;

import com.taskFlow.dto.LoginDto;
import com.taskFlow.dto.SignUpDto;
import com.taskFlow.response.LoginResponse;
import com.taskFlow.response.SignupResponse;
import com.taskFlow.service.AuthService;
import com.taskFlow.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/task-flow/")
@AllArgsConstructor
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final AuthService authService;

    @PostMapping("sign-up")
    ResponseEntity<SignupResponse> userSignUp(@Valid @RequestBody SignUpDto signUpDto) {
      SignupResponse signupResponse = authService.userSignUp(signUpDto);
      if (signupResponse != null) {
        return ResponseEntity.status(HttpStatus.OK).body(signupResponse);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
    }

  @PostMapping("login")
  ResponseEntity<LoginResponse> userLogin(@Valid @RequestBody LoginDto loginDto) {

    LoginResponse loginResponse = authService.userLogin(loginDto);

    if (loginResponse != null) {
      return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
