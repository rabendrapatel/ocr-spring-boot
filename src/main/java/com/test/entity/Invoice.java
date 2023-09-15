package com.test.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "tbl_invoice_details")
@EqualsAndHashCode(callSuper=false)
public class Invoice extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fld_id")
    private Long id;

    @Lob
    @Column(name = "fld_invoice_no")
    private String invoiceNumber;
    
    @Column(name = "fld_invoice_date")
    private LocalDate invoiceDate;
    
    @Lob
    @Column(name = "fld_supplier_name")
    private String supplierName;
    
    @Column(name = "fld_amount")
    private Double amount;
}
