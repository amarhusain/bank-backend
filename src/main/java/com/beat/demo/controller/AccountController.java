package com.beat.demo.controller;

import com.beat.demo.payload.request.LoginRequest;
import com.beat.demo.payload.response.JwtResponse;
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
import org.springframework.web.bind.annotation.RequestParam;

public interface AccountController {

    /**
     * Authenticates User
     *
     * @param accountNumber account
     * @param email email
     * @return the user
     */
    @Tag(name = "Auth", description = "Auth API")
    @PostMapping("/signin")
    @Operation(summary = "Create user", description = "Returns message", tags = "User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created Successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<com.beat.demo.payload.response.ApiResponse<Void>> createAccount(
            @RequestParam String accountNumber, @RequestParam String email);
}
