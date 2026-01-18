package com.taskFlow.model;

import com.taskFlow.constants.DBCollections;
import com.taskFlow.enums.UserType;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Document(collection = DBCollections.USERS)
@Builder
public class User implements UserDetails {
    @Id
    private ObjectId Id;
    private ObjectId companyId;
    private ObjectId departmentId;
    private String name;
    private String phoneNumber;
    @Indexed(unique = true)   // this is for unique identification
    private String email;
    @Indexed(unique = true)
    private String loginId;
    private String password;
    private Boolean isActive;
    private Boolean isRemoved;
    private List<String> privileges;
    private UserType userType;
    private Long createdOn;
    private ObjectId createdBy;
    private Long lastUpdatedOn;
    private ObjectId lastUpdatedBy;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
