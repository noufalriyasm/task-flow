package com.taskFlow.seviceImpl;

import com.taskFlow.constants.Messages;
import com.taskFlow.dto.LoginDto;
import com.taskFlow.dto.SignUpDto;
import com.taskFlow.enums.UserType;
import com.taskFlow.mapper.SignupMapper;
import com.taskFlow.model.Company;
import com.taskFlow.model.Department;
import com.taskFlow.model.User;
import com.taskFlow.repository.CompanyRepository;
import com.taskFlow.repository.DepartmentRepository;
import com.taskFlow.repository.UserRepository;
import com.taskFlow.response.CommonIdNameResponse;
import com.taskFlow.response.LoginResponse;
import com.taskFlow.response.SignupResponse;
import com.taskFlow.service.AuthService;
import com.taskFlow.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final DepartmentRepository departmentRepository;
  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SignupMapper signupMapper;

  @Override
  public LoginResponse userLogin(LoginDto loginDto) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPassword()));
    User loggedInUser = (User) authentication.getPrincipal();
    assert loggedInUser != null;
    final String jwtToken = jwtUtil.generateToken(loggedInUser);

    Department userDepartment =
        departmentRepository.findDepartmentById(
            loggedInUser.getCompanyId().toHexString(), loggedInUser.getDepartmentId().toHexString());
    Company userCompany = companyRepository.findCompanyById(loggedInUser.getCompanyId().toHexString());

    CommonIdNameResponse departmentResponse = null;
    CommonIdNameResponse companyResponse = null;
    if (userDepartment != null) {
      departmentResponse =
          CommonIdNameResponse.builder()
              .id(userDepartment.getId())
              .name(userDepartment.getName())
              .build();
    }

    if (userCompany != null) {
      companyResponse =
          CommonIdNameResponse.builder()
              .id(userCompany.getId())
              .name(userCompany.getName())
              .build();
    }
    return LoginResponse.builder()
        .status(1)
        .message(Messages.LOGGED_IN_SUCCESSFULLY)
        .id(loggedInUser.getId())
        .name(loggedInUser.getName())
        .email(loggedInUser.getEmail())
        .token(jwtToken)
        .department(departmentResponse)
        .company(companyResponse)
        .build();
  }

  // mongodb connection check
  //  @PostConstruct
  //  public void checkMongoDb() {
  //    System.out.println("Mongo DB in use = " + mongoTemplate.getDb().getName());
  //  }

  @Override
  public SignupResponse userSignUp(SignUpDto signUpDto) {

    String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

    User user = signupMapper.toEntity(signUpDto);
    user.setUserType(UserType.ADMIN);
    user.setIsActive(Boolean.TRUE);
    user.setIsRemoved(Boolean.FALSE);
    user.setCreatedOn(System.currentTimeMillis());
    user.setLastUpdatedOn(System.currentTimeMillis());
    user.setPassword(encodedPassword);
    User savedUser = userRepository.saveUser(user);

    savedUser.setLastUpdatedBy(new ObjectId(savedUser.getId()));
    savedUser.setCreatedBy(new ObjectId(savedUser.getId()));

    userRepository.saveUser(savedUser);

    return SignupResponse.builder()
        .status(1)
        .id(savedUser.getId())
        .name(savedUser.getName())
        .message(Messages.USER_CREATED_SUCCESSFULLY)
        .build();
  }
}
