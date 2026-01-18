package com.taskFlow.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommonSuccessResponse {
    private Integer status;
    private String id;
    private String name;
    private String message;
}
