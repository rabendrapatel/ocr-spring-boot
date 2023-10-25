package com.source.dto.master.user;

import lombok.Data;

@Data
public class UserDetailsDto {
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
    private CompanyDto companyMaster;
    
    @Data
    private static class CompanyDto {
	  	private String companyName;
	    private String companyCode;
	    private String companySortName;
	    private String photo;
    }
}
