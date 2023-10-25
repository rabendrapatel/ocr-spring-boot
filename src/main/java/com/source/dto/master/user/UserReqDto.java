package com.source.dto.master.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReqDto {
    private Long userId;
    private String userName;
    private String password;
    private String email;
    private String mobileNo;
    private String isEmailVerify;
    private String isMobileVerify;
    private Integer status;
    private String firstName;
    private String lastName;
    private Long roleId;
    private Long companyId;
    private String photo;
    private String token;
}
