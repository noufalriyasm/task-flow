package com.taskFlow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpDto {
  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Login id is mandatory")
  private String loginId;

  @NotBlank(message = "Phone number is mandatory")
  @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
  private String phoneNumber;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email is mandatory")
  private String email;

  @NotBlank(message = "Password is mandatory")
  private String password;
}
