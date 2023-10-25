package com.source.service.tran.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.source.config.jwt.UserPrincipal;
import com.source.dto.tran.docs.DocumentListDto;
import com.source.dto.tran.docs.DocumentReqDto;

public interface DocumentService {

	Boolean importDocument(MultipartFile file, UserPrincipal user);

	Page<DocumentListDto> getDocumentList(Pageable pageable, DocumentReqDto req, UserPrincipal user);

}
