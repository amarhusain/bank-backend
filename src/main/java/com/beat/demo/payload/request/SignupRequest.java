package com.beat.demo.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

  @NotBlank
  @Size(max = 100)
  @Email
  private String email;

  @NotBlank
  @Size(min = 4, max = 40)
  private String password;

}
