package com.source.master.repository.user;

import org.springframework.stereotype.Repository;

import com.source.master.entity.user.UserMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

	Page<UserMaster> findAll(Specification<UserMaster> spec, Pageable pageable);

	UserMaster findByUserName(String username);

	UserMaster findByUserId(Long userId);

}