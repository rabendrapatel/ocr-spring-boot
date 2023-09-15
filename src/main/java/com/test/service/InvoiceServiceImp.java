package com.test.service;

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

import com.test.dto.InvoiceDto;
import com.test.dto.InvoiceReqDto;
import com.test.entity.Invoice;
import com.test.helper.InvoiceHelper;
import com.test.repository.InvoiceRepository;
import com.test.specification.InvoiceSpecification;


@Service
public class InvoiceServiceImp implements InvoiceService {

	@Autowired
	ModelMapper m;
	
	@Autowired
	InvoiceHelper helper;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Override
	public Boolean importInvoice(MultipartFile file) throws Exception {
		if (file.getContentType().equals("application/pdf")) {
            // If it's a PDF, convert it to images
            String data = helper.processPdf(file);
            Invoice invoice = helper.processOCRData(data);
            invoiceRepository.save(invoice);
            return true;
        } else if (file.getContentType().startsWith("image/")) {
            // If it's an PNG image, perform OCR directly
        	String data = helper.processImage(file);
        	Invoice invoice = helper.processOCRData(data);
        	invoiceRepository.save(invoice);
        	return true;
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }
	}

	@Override
	public Page<InvoiceDto> findAllInvoice(Pageable pageable, InvoiceReqDto req) throws Exception {
		Specification<Invoice> spec = InvoiceSpecification.getInvoiceFilter(req);
		Page<Invoice> list = invoiceRepository.findAll(spec,pageable);
		
		List<InvoiceDto> resultAL = list.stream().map(elm -> {
			InvoiceDto dto = m.map(elm, InvoiceDto.class);
			return dto;
		}).collect(Collectors.toList());
		
		return new PageImpl<>(resultAL, pageable, list.getTotalElements());
	}

	

}
