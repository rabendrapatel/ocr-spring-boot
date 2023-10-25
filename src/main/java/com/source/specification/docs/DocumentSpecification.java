package com.source.specification.docs;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.source.dto.tran.docs.DocumentReqDto;
import com.source.entity.tran.docs.Document;

public class DocumentSpecification  {


	public static Specification<Document> getDocumentFilter(DocumentReqDto req) {
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
