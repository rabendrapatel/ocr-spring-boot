package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.test.dto.InvoiceDto;
import com.test.dto.InvoiceReqDto;
import com.test.service.InvoiceService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/invoice")
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;

	@PostMapping("/import")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
			}
			Boolean isSuccess = invoiceService.importInvoice(file);
			if (isSuccess) {
				return new ResponseEntity<>("Invoice uploaded successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invoice upload failed - ", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Invoice upload failed - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/list")
	public ResponseEntity<?> getInvoiceList(
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable pageable,
			@RequestBody InvoiceReqDto req ) {
		try {
			Page<InvoiceDto> list = invoiceService.findAllInvoice(pageable,req);

			if (list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record found.");
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
