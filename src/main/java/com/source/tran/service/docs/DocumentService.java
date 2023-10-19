package com.source.tran.service.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.source.config.jwt.UserPrincipal;
import com.source.tran.dto.docs.DocumentListDto;
import com.source.tran.dto.docs.DocumentReqDto;

public interface DocumentService {

	Boolean importDocument(MultipartFile file, UserPrincipal user);

	Page<DocumentListDto> getDocumentList(Pageable pageable, DocumentReqDto req, UserPrincipal user);

}
