package com.source.entity.general;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

	@Basic
	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "fld_created_at", nullable = true)
	protected LocalDateTime createdAt;

	@Basic
	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "fld_updated_at", nullable = true)
	protected LocalDateTime updatedAt;
	
	@Column(name="fld_created_by")
    private Long createdBy;

	@Column(name="fld_updated_by")
    private Long updatedBy;
	
	@Column(name="fld_company_id")
	private Long companyId;

}
