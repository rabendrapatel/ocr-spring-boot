package com.source.specification.user;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.source.dto.master.user.UserReqDto;
import com.source.entity.master.user.UserMaster;

public class UserSpecification  {


	public static Specification<UserMaster> createUserListFilter(UserReqDto req) {
		return (root, query, builder) -> {
			List<Predicate> list = new ArrayList<>();
			if (req.getUserName() != null && !req.getUserName().isEmpty()) {
			    list.add(builder.like(root.get("userName"), "%" + req.getUserName() + "%"));
			}
			if (req.getEmail() != null && !req.getEmail().isEmpty()) {
			    list.add(builder.like(root.get("email"), "%" + req.getEmail() + "%"));
			}
			if (req.getMobileNo() != null && !req.getMobileNo().isEmpty()) {
			    list.add(builder.like(root.get("mobileNo"), "%" + req.getMobileNo() + "%"));
			}
			Predicate[] p = new Predicate[list.size()];
			return builder.and(list.toArray(p));
		};
	}
}
