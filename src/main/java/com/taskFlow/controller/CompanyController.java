package com.taskFlow.controller;

import com.taskFlow.dto.CompanyDto;
import com.taskFlow.response.CommonSuccessResponse;
import com.taskFlow.response.company.GetCompanyDetailsResponse;
import com.taskFlow.response.company.GetCompanyListResponse;
import com.taskFlow.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/task-flow/company/")
public class CompanyController {
  @Autowired private CompanyService companyService;

  @PostMapping("create")
  ResponseEntity<CommonSuccessResponse> createCompany(
      @Valid @RequestBody final CompanyDto companyDto) {
    CommonSuccessResponse createCompanySuccess = companyService.createCompany(companyDto);

    if (createCompanySuccess != null) {
      return ResponseEntity.status(HttpStatus.OK).body(createCompanySuccess);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PutMapping("update-company/{companyId}")
  ResponseEntity<CommonSuccessResponse> updateCompany(
      @PathVariable final String companyId, @Valid @RequestBody final CompanyDto companyDto) {
    CommonSuccessResponse updateCompanyResponse =
        companyService.updateCompany(companyId, companyDto);

    if (updateCompanyResponse != null) {
      return ResponseEntity.status(HttpStatus.OK).body(updateCompanyResponse);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("get-company-list")
  ResponseEntity<GetCompanyListResponse> getCompanyList() {
    GetCompanyListResponse companyListResponse = companyService.getCompanyList();

    if (companyListResponse != null) {
      return ResponseEntity.status(HttpStatus.OK).body(companyListResponse);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("get-company-details/{companyId}")
  ResponseEntity<GetCompanyDetailsResponse> getCompanyDetails(@PathVariable final String companyId) {
    GetCompanyDetailsResponse companyDetailsResponse = companyService.getCompanyDetails(companyId);

    if (companyDetailsResponse != null) {
      return ResponseEntity.status(HttpStatus.OK).body(companyDetailsResponse);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
