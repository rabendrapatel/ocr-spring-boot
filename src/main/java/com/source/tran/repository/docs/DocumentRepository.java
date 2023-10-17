package com.source.tran.repository.docs;

import org.springframework.stereotype.Repository;

import com.source.tran.entity.docs.Document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	Page<Document> findAll(Specification<Document> spec,Pageable pageable);

}
