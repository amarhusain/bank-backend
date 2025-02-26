package com.beat.demo.service;

import com.beat.demo.entity.Role;
import com.beat.demo.entity.User;
import com.beat.demo.enums.ERole;
import com.beat.demo.exception.UnauthorizedException;
import com.beat.demo.payload.request.LoginRequest;
import com.beat.demo.payload.request.SignupRequest;
import com.beat.demo.payload.response.ApiResponse;
import com.beat.demo.payload.response.JwtResponse;
import com.beat.demo.payload.response.UserResponse;
import com.beat.demo.repository.RoleRepository;
import com.beat.demo.repository.UserRepository;
import com.beat.demo.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;
  private final RoleRepository roleRepository;

  public AuthServiceImpl(
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      JwtUtils jwtUtils) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.encoder = passwordEncoder;
    this.jwtUtils = jwtUtils;
  }

  @Override
  public ApiResponse<JwtResponse> authenticate(@Valid @RequestBody LoginRequest request) {

    User user = userRepository.findByEmail(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found!"));

    if (!encoder.matches(request.getPassword(), user.getPassword())) {
      throw new UnauthorizedException("Invalid credentials");
    }

     JwtResponse jwtResponse = JwtResponse.builder()
          .token(jwtUtils.generateJwtToken(user.getEmail()))
          .id(user.getId())
          .email(user.getEmail())
          .roles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
          .build();
   return new ApiResponse<>("User Logged In successfully ", jwtResponse);
  }


  @Override
  @Transactional
  public ApiResponse<UserResponse> register(SignupRequest request) {
    Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
    if (existingUser.isPresent()) {
      throw new RuntimeException("User already exists!");
    }

    // Assign the default role to the new user
    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Role not found"));

    // Initialize a set to hold the roles
    Set<Role> userRoles = new HashSet<>();
    userRoles.add(userRole); // Add the default role

    User newUser = User.builder()
            .email(request.getEmail())
            .password(encoder.encode(request.getPassword()))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .isActive(true)
            .roles(userRoles)
            .build();

    userRepository.save(newUser);

    return new ApiResponse<>( "Registration successfull", UserResponse.builder()
             .email(newUser.getEmail())
             .build());
  }

}
