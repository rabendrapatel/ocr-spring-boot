package com.source.service.tran.docs;

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
import com.source.dto.tran.docs.DocumentListDto;
import com.source.dto.tran.docs.DocumentReqDto;
import com.source.entity.tran.docs.Document;
import com.source.helper.tran.docs.DocumentHelper;
import com.source.repository.tran.docs.DocumentRepository;
import com.source.specification.docs.DocumentSpecification;

@Service
public class DocumentServiceImp implements DocumentService {

	@Autowired
	ModelMapper m;

	@Autowired
	DocumentHelper helper;

	@Autowired
	DocumentRepository documentRepo;

	@Override
	public Boolean importDocument(MultipartFile file, UserPrincipal user) {
		try {
			if (file.isEmpty()) {
				throw new RuntimeException("File is empty");
			}else if (file.getContentType().equals("application/pdf")) {
				// If it's a PDF, convert it to images
				String data = helper.processPdf(file);
				Document invoice = helper.processOCRData(data);
				invoice.setCreatedBy(user.getUserId());
				invoice.setUpdatedBy(user.getUserId());
				invoice.setCompanyId(user.getCompanyId());
				documentRepo.save(invoice);
				return true;
			} else if (file.getContentType().startsWith("image/")) {
				// If it's an PNG image, perform OCR directly
				String data = helper.processImage(file);
				Document invoice = helper.processOCRData(data);
				invoice.setCreatedBy(user.getUserId());
				invoice.setUpdatedBy(user.getUserId());
				invoice.setCompanyId(user.getCompanyId());
				documentRepo.save(invoice);
				return true;
			} else {
				throw new RuntimeException("Unsupported file type");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Page<DocumentListDto> getDocumentList(Pageable pageable, DocumentReqDto req, UserPrincipal user) {
		Specification<Document> spec = DocumentSpecification.getDocumentFilter(req);
		Page<Document> list = documentRepo.findAll(spec, pageable);

		List<DocumentListDto> resultAL = list.stream().map(elm -> {
			DocumentListDto dto = m.map(elm, DocumentListDto.class);
			return dto;
		}).collect(Collectors.toList());

		return new PageImpl<>(resultAL, pageable, list.getTotalElements());
	}

}
