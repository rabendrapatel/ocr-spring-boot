package com.test.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class InvoiceDto {
    private Long id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private String supplierName;
    private Double amount;
}
