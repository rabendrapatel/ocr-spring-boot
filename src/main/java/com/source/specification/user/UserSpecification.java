package com.source.specification.user;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.source.master.dto.user.UserReqDto;
import com.source.master.entity.user.UserMaster;

public class UserSpecification  {


	public static Specification<UserMaster> createUserListFilter(UserReqDto req) {
		return (root, query, builder) -> {
			List<Predicate> list = new ArrayList<>();
			//if (req.getInvoiceNumber() != null && !req.getInvoiceNumber().isEmpty()) {
			//	list.add(builder.equal(root.get("invoiceNumber"),req.getInvoiceNumber()));
			//}

			Predicate[] p = new Predicate[list.size()];
			return builder.and(list.toArray(p));
		};
	}
}
