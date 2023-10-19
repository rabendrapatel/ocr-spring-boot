package com.source.tran.controller.docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.source.config.jwt.CurrentUser;
import com.source.config.jwt.UserPrincipal;
import com.source.response.ResponseRes;
import com.source.tran.dto.docs.DocumentListDto;
import com.source.tran.dto.docs.DocumentReqDto;
import com.source.tran.service.docs.DocumentService;

@RestController
@RequestMapping(value = "/api/tran/docs")
public class DocumentController {

	@Autowired
	DocumentService documentService;

	@PostMapping("/import")
	public ResponseEntity<?> importDocument(@RequestParam("file") MultipartFile file, @CurrentUser UserPrincipal user) {
		try {
			if (file.isEmpty()) {
				return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
			}
			Boolean isSuccess = documentService.importDocument(file, user);
			if (isSuccess) {
				return new ResponseEntity<>("Document uploaded successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Document upload failed - ", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Document upload failed - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/list")
	public ResponseEntity<?> getDocumentList(
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable pageable,
			@RequestBody DocumentReqDto req, @CurrentUser UserPrincipal user) {
		try {
			Page<DocumentListDto> list = documentService.getDocumentList(pageable, req, user);
			if (!list.isEmpty()) {
				return ResponseEntity
						.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Record found", list));
			} else {
				return ResponseEntity
						.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "No record found", list));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}

}
