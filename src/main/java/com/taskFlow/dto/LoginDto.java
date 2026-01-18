package com.taskFlow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "Login id cannot be null")
    private String loginId;
    @NotBlank(message = "Password cannot be null")
    private String password;
}
