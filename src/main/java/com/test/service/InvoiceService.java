package com.test.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.test.dto.InvoiceDto;
import com.test.dto.InvoiceReqDto;

public interface InvoiceService {
	
	Boolean importInvoice(MultipartFile file) throws Exception;
	
	Page<InvoiceDto> findAllInvoice(Pageable pageable, InvoiceReqDto req) throws Exception;


}
