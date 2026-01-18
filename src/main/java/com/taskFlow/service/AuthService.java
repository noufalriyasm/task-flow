package com.taskFlow.service;

import com.taskFlow.dto.LoginDto;
import com.taskFlow.dto.SignUpDto;
import com.taskFlow.response.LoginResponse;
import com.taskFlow.response.SignupResponse;
import jakarta.validation.Valid;

public interface AuthService {

    LoginResponse userLogin(final LoginDto loginDto);

    SignupResponse userSignUp(final SignUpDto signUpDto);
}
