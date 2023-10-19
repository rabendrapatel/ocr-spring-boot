package com.source.master.repository.user;

import org.springframework.stereotype.Repository;

import com.source.master.entity.user.UserPasswordResetLink;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserPasswordResetLinkRepository extends JpaRepository<UserPasswordResetLink, Long> {

	UserPasswordResetLink findByToken(String token);

}
