package com.taskFlow.response.company;

import com.taskFlow.response.CommonIdNameResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCompanyDetailsResponse {
  private Integer status;
  private GetCompanyDetailsMiniResponse company;

  @Data
  @Builder
  public static class GetCompanyDetailsMiniResponse {
    private String id;
    private String name;
    private Boolean isActive;
    private CommonIdNameResponse createdBy;
    private Long createdOn;
    private CommonIdNameResponse lastUpdatedBy;
    private Long lastUpdatedOn;
  }
}
