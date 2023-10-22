package com.source.master.repository.user;

import org.springframework.stereotype.Repository;

import com.source.master.entity.user.UserMaster;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

	Page<UserMaster> findAll(Specification<UserMaster> spec, Pageable pageable);

	UserMaster findByUserName(String username);

	UserMaster findByUserId(Long userId);

	Optional<UserMaster> findByEmail(String email);
	
	Optional<UserMaster> findByMobileNo(String mobileNo);

	int countByEmailAndUserIdNot(String email, Long userId);

	int countByEmailAndUserId(String email, Long userId);

	@EntityGraph(attributePaths = {"companyMaster"})
	Optional<UserMaster> findUserDetaisByUserId(Long userId);

}
