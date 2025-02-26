package com.beat.demo.service;

import com.beat.demo.payload.request.LoginRequest;
import com.beat.demo.payload.request.SignupRequest;
import com.beat.demo.payload.response.ApiResponse;
import com.beat.demo.payload.response.JwtResponse;
import com.beat.demo.payload.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

  public ApiResponse<JwtResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest);

  public ApiResponse<UserResponse> register(SignupRequest request);
}
