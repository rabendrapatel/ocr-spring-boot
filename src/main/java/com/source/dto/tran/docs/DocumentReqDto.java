package com.source.dto.tran.docs;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DocumentReqDto {
    private String invoiceNumber;
    private LocalDate invoiceDate;
}
