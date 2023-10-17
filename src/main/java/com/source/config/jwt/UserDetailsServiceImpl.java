package com.source.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.source.master.entity.user.UserMaster;
import com.source.master.repository.user.UserMasterRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserMasterRepository userMasterRepo;

	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserMaster user = userMasterRepo.findByUserName(username);
		return UserPrincipal.create(user);
	}
	
	
    public UserDetails cacheUserById(Long userId) {
    	UserMaster user = userMasterRepo.findByUserId(userId);
        return UserPrincipal.create(user);
    }

}
