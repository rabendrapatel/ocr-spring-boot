package com.test.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class InvoiceReqDto {
    private String invoiceNumber;
    private LocalDate invoiceDate;
}
