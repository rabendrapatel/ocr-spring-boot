package com.source.master.entity.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tbl_master_company")
@EqualsAndHashCode(callSuper=false)
public class CompanyMaster {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fld_company_id")
    private Long companyId;

	@Lob
    @Column(name = "fld_company_name")
    private String companyName;
    
	@Lob
    @Column(name = "fld_company_sory_name")
    private String companySortName;
    
	
    @Column(name = "fld_company_code")
    private String companyCode;
    
    @Column(name = "fld_status")
    private Integer status=0;
    
    @Column(name = "fld_photo")
    private String photo;
}
