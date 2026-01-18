package com.taskFlow.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponse {
    private Integer status;
    private String id;
    private String name;
    private String message;
}
