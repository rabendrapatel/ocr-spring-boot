package com.source.config.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.source.master.entity.user.UserMaster;

import lombok.Data;

@Data
public class UserPrincipal implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String isEmailVerify="No";
    private String isMobileVerify="No";
    private Integer status;
    private Long roleId;
    private Long companyId;
    private String photo;


    public UserPrincipal(UserMaster user) {
		this.userId = user.getUserId();
		this.userName =user.getUserName();
		this.firstName=user.getFirstName();
		this.lastName = user.getLastName();
		this.email=user.getEmail();
		this.mobileNo=user.getMobileNo();
		this.isEmailVerify=user.getIsEmailVerify();
		this.isMobileVerify=user.getIsMobileVerify();
		this.status=user.getStatus();
		this.roleId=user.getRoleId();
		this.photo=user.getPhoto();
		this.companyId=user.getCompanyId();
		this.password = user.getPassword();
		
	}

	public static UserPrincipal create(UserMaster user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(""+user.getRoleId()));
		return new UserPrincipal(user);
	}


	
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
}
