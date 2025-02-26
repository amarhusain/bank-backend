package com.beat.demo.controller;

import com.beat.demo.payload.request.LoginRequest;
import com.beat.demo.payload.request.SignupRequest;
import com.beat.demo.payload.response.ApiResponse;
import com.beat.demo.payload.response.JwtResponse;
import com.beat.demo.payload.response.UserResponse;
import com.beat.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {

  private final AuthService authService;

  @Autowired
  public AuthControllerImpl(AuthService authService) {
    this.authService = authService;
  }


  @Override
  @PostMapping("/signin")
  public ResponseEntity<ApiResponse<JwtResponse>> signin(
      @Valid @RequestBody LoginRequest loginRequest) {
      ApiResponse<JwtResponse> response = authService.authenticate(loginRequest);

      return ResponseEntity.ok(response);

  }

  @Override
  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<UserResponse>> signup(
      @Valid @RequestBody SignupRequest signupRequest) {
    ApiResponse<UserResponse> response = authService.register(signupRequest);
    return ResponseEntity.ok(response);
  }



}