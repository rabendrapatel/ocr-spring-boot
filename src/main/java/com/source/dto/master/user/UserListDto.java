package com.source.dto.master.user;

import lombok.Data;

@Data
public class UserListDto {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String isEmailVerify;
    private String isMobileVerify;
    private Integer status=0;
    private Long roleId;
    private Long companyId;
    private String photo;
}
