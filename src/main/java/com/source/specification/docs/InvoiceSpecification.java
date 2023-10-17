package com.source.specification.docs;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.source.tran.dto.docs.DocumentReqDto;
import com.source.tran.entity.docs.Document;

public class InvoiceSpecification  {


	public static Specification<Document> getInvoiceFilter(DocumentReqDto req) {
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
