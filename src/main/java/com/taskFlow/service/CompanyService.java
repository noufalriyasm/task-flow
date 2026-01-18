package com.taskFlow.service;

import com.taskFlow.dto.CompanyDto;
import com.taskFlow.response.CommonSuccessResponse;
import com.taskFlow.response.company.GetCompanyDetailsResponse;
import com.taskFlow.response.company.GetCompanyListResponse;

public interface CompanyService {

    CommonSuccessResponse createCompany(final CompanyDto companyDto);

    GetCompanyListResponse getCompanyList();

    CommonSuccessResponse updateCompany(final String companyId,final CompanyDto companyDto);

    GetCompanyDetailsResponse getCompanyDetails(final String companyId);
}
