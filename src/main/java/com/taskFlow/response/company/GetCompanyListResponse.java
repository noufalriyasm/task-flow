package com.taskFlow.response.company;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCompanyListResponse {
    private Integer status;
    private List<GetCompanyListMiniResponse> companyList;

    @Data
    @Builder
    public static class GetCompanyListMiniResponse{
        private String id;
        private String name;
        private Boolean isActive;
    }
}
