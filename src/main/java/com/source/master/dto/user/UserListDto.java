package com.source.master.dto.user;

import lombok.Data;

@Data
public class UserListDto {
	private Long userId;
    private String userName;
    private String password;
    private String email;
    private String mobileNo;
    private String isEmailVerify;
    private String isMobileVerify;
    private Integer status;
}
