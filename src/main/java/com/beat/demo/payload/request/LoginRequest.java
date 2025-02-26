package com.beat.demo.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank
  private String username; // which is email

  @NotBlank
  private String password;

}
