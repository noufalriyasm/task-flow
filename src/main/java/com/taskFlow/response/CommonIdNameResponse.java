package com.taskFlow.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommonIdNameResponse {
    private String id;
    private String name;
}
