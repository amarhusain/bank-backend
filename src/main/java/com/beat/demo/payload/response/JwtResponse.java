package com.beat.demo.payload.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

  private String token;
  private Long id;
  private String email;
  private List<String> roles;

}
