package com.source.dto.tran.docs;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DocumentListDto {
    private Long id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private String supplierName;
    private Double amount;
}
