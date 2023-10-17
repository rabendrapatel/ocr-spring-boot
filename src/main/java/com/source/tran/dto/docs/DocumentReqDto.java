package com.source.tran.dto.docs;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DocumentReqDto {
    private String invoiceNumber;
    private LocalDate invoiceDate;
}
