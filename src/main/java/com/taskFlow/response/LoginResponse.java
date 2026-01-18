package com.taskFlow.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class LoginResponse {
    private Integer status;
    private String message;
    private CommonIdNameResponse company;
    private CommonIdNameResponse department;
    private String id;
    private String name;
    private String token;
    private List<String> privileges;
    private String userType;
    private String email;
    private String phoneNumber;
}
