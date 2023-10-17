package com.source.tran.service.docs;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.source.config.jwt.UserPrincipal;
import com.source.specification.docs.InvoiceSpecification;
import com.source.tran.dto.docs.DocumentListDto;
import com.source.tran.dto.docs.DocumentReqDto;
import com.source.tran.entity.docs.Document;
import com.source.tran.helper.docs.DocumentHelper;
import com.source.tran.repository.docs.DocumentRepository;

@Service
public class DocumentServiceImp implements DocumentService {

	@Autowired
	ModelMapper m;

	@Autowired
	DocumentHelper helper;

	@Autowired
	DocumentRepository invoiceRepository;

	@Override
	public Boolean importDocument(MultipartFile file, UserPrincipal user) throws Exception {
		if (file.getContentType().equals("application/pdf")) {
			// If it's a PDF, convert it to images
			String data = helper.processPdf(file);
			Document invoice = helper.processOCRData(data);
			invoiceRepository.save(invoice);
			return true;
		} else if (file.getContentType().startsWith("image/")) {
			// If it's an PNG image, perform OCR directly
			String data = helper.processImage(file);
			Document invoice = helper.processOCRData(data);
			invoiceRepository.save(invoice);
			return true;
		} else {
			throw new IllegalArgumentException("Unsupported file type");
		}
	}

	@Override
	public Page<DocumentListDto> getDocumentList(Pageable pageable, DocumentReqDto req, UserPrincipal user) {
		Specification<Document> spec = InvoiceSpecification.getInvoiceFilter(req);
		Page<Document> list = invoiceRepository.findAll(spec, pageable);

		List<DocumentListDto> resultAL = list.stream().map(elm -> {
			DocumentListDto dto = m.map(elm, DocumentListDto.class);
			return dto;
		}).collect(Collectors.toList());

		return new PageImpl<>(resultAL, pageable, list.getTotalElements());
	}

}
