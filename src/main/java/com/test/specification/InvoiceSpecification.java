package com.test.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.test.dto.InvoiceReqDto;
import com.test.entity.Invoice;

public class InvoiceSpecification  {


	public static Specification<Invoice> getInvoiceFilter(InvoiceReqDto req) {
		return (root, query, builder) -> {
			List<Predicate> list = new ArrayList<>();
			if (req.getInvoiceNumber() != null && !req.getInvoiceNumber().isEmpty()) {
				list.add(builder.equal(root.get("invoiceNumber"),req.getInvoiceNumber()));
			}

			Predicate[] p = new Predicate[list.size()];
			return builder.and(list.toArray(p));
		};
	}
}
