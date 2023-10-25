package com.source.repository.master.user;

import org.springframework.stereotype.Repository;

import com.source.entity.master.user.UserPasswordResetLink;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserPasswordResetLinkRepository extends JpaRepository<UserPasswordResetLink, Long> {

	UserPasswordResetLink findByToken(String token);

}
