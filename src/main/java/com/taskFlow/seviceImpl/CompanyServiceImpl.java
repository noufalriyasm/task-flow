package com.taskFlow.seviceImpl;

import com.taskFlow.constants.Messages;
import com.taskFlow.dto.CompanyDto;
import com.taskFlow.enums.UserType;
import com.taskFlow.exceptions.BusinessExceptions;
import com.taskFlow.model.Company;
import com.taskFlow.model.User;
import com.taskFlow.repository.CompanyRepository;
import com.taskFlow.response.CommonIdNameResponse;
import com.taskFlow.response.CommonSuccessResponse;
import com.taskFlow.response.company.GetCompanyDetailsResponse;
import com.taskFlow.response.company.GetCompanyListResponse;
import com.taskFlow.service.AuthenticationFacade;
import com.taskFlow.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final AuthenticationFacade authenticationFacade;

  @Override
  public CommonSuccessResponse createCompany(CompanyDto companyDto) {
    User loggedInUser = (User) authenticationFacade.getAuthentication().getPrincipal();

    if (loggedInUser.getUserType() != UserType.ADMIN) {
      throw new BusinessExceptions(Messages.YOU_ARE_NOT_AUTHORIZED_TO_CREATE_COMPANY);
    }

    Company company =
        Company.builder()
            .name(companyDto.getName())
            .isActive(companyDto.getIsActive())
            .isRemoved(false)
            .createdBy(loggedInUser.getId())
            .createdOn(System.currentTimeMillis())
            .lastUpdatedBy(loggedInUser.getId())
            .lastUpdatedOn(System.currentTimeMillis())
            .build();

    Company savedCompany = companyRepository.saveCompany(company);
    return CommonSuccessResponse.builder()
        .id(savedCompany.getId().toHexString())
        .status(1)
        .name(savedCompany.getName())
        .message(String.format(Messages.CREATED_SUCCESSFULLY, "Company"))
        .build();
  }

  @Override
  public GetCompanyListResponse getCompanyList() {
    User loggedInUser = (User) authenticationFacade.getAuthentication().getPrincipal();
    List<Company> companyList =
        companyRepository.getCompanyList(loggedInUser.getId().toHexString());

    List<GetCompanyListResponse.GetCompanyListMiniResponse> companyListMiniResponse =
        companyList.stream()
            .map(
                company ->
                    GetCompanyListResponse.GetCompanyListMiniResponse.builder()
                        .id(company.getId().toHexString())
                        .name(company.getName())
                        .isActive(company.getIsActive())
                        .build())
            .toList();
    return GetCompanyListResponse.builder().status(1).companyList(companyListMiniResponse).build();
  }

  @Override
  public CommonSuccessResponse updateCompany(String companyId, CompanyDto companyDto) {
    User loggedInUser = (User) authenticationFacade.getAuthentication().getPrincipal();

    Company company = companyRepository.getCompanyById(companyId);

    if (company == null) {
      throw new BusinessExceptions(String.format(Messages.NOT_FOUND, "Company"));
    }

    if (!company.getCreatedBy().toHexString().equals(loggedInUser.getId().toHexString())) {
      throw new BusinessExceptions(Messages.YOU_ARE_NOT_AUTHORIZED_TO_UPDATE_THIS_COMPANY);
    }

    company.setName(companyDto.getName());
    company.setIsActive(companyDto.getIsActive());
    company.setLastUpdatedBy(loggedInUser.getId());
    company.setLastUpdatedOn(System.currentTimeMillis());

    Company updatedCompany = companyRepository.saveCompany(company);
    return CommonSuccessResponse.builder()
        .status(1)
        .message(String.format(Messages.UPDATED_SUCCESSFULLY, updatedCompany.getName()))
        .name(updatedCompany.getName())
        .id(updatedCompany.getId().toHexString())
        .build();
  }

  @Override
  public GetCompanyDetailsResponse getCompanyDetails(String companyId) {
    Company company = companyRepository.getCompanyById(companyId);
    User loggedInUser = (User) authenticationFacade.getAuthentication().getPrincipal();
    if (company == null) {
      throw new BusinessExceptions(String.format(Messages.NOT_FOUND, "Company"));
    }
    if (!company.getCreatedBy().equals(loggedInUser.getId())) {
      throw new BusinessExceptions(Messages.UNAUTHORIZED_USER);
    }
    CommonIdNameResponse createdByUser =
        CommonIdNameResponse.builder()
            .name(loggedInUser.getName())
            .id(loggedInUser.getId().toHexString())
            .build();
    GetCompanyDetailsResponse.GetCompanyDetailsMiniResponse companyDetailsMiniResponse =
        GetCompanyDetailsResponse.GetCompanyDetailsMiniResponse.builder()
            .id(company.getId().toHexString())
            .name(company.getName())
            .isActive(company.getIsActive())
            .createdBy(createdByUser)
            .createdOn(company.getCreatedOn())
            .lastUpdatedBy(createdByUser)
            .lastUpdatedOn(company.getLastUpdatedOn())
            .build();

    return GetCompanyDetailsResponse.builder()
        .status(1)
        .company(companyDetailsMiniResponse)
        .build();
  }
}
