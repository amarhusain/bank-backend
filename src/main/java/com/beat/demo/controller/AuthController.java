package com.beat.demo.controller;

import com.beat.demo.payload.request.LoginRequest;
import com.beat.demo.payload.request.SignupRequest;
import com.beat.demo.payload.response.JwtResponse;
import com.beat.demo.payload.response.UserResponse;
import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth", description = "Auth API")
@RequestMapping("/api/auth")
public interface AuthController {


  /**
   * Authenticates User
   *
   * @param loginRequest the user
   * @return the user
   */
  @Tag(name = "Auth", description = "Auth API")
  @PostMapping("/signin")
  @Operation(summary = "Authenticate user", description = "Returns Jwt", tags = "User")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successful authentication",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request - Invalid credentials", content = @Content),
          @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
     })
  public ResponseEntity<com.beat.demo.payload.response.ApiResponse<JwtResponse>> signin(
      @Valid @RequestBody LoginRequest loginRequest);

  /**
   * Creates new User
   *
   * @param signupRequest the user
   * @return the user
   */
  @Tag(name = "Auth", description = "Auth API")
  @PostMapping("/signup")
  @Operation(summary = "Creates user", description = "Returns created user", tags = "User")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  public ResponseEntity<com.beat.demo.payload.response.ApiResponse<UserResponse>> signup(
      @Valid @RequestBody SignupRequest signupRequest);

}