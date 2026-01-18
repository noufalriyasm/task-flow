package com.taskFlow.mapper;

import com.taskFlow.dto.SignUpDto;
import com.taskFlow.model.User;
import org.springframework.stereotype.Component;

@Component
public class SignupMapper {
  public User toEntity(SignUpDto signUpDto) {
    return User.builder()
        .name(signUpDto.getName())
        .email(signUpDto.getEmail())
        .phoneNumber(signUpDto.getPhoneNumber())
        .loginId(signUpDto.getLoginId())
        .build();
  }
}
