package com.personalfinance.utsanisaalawiyah.dto;
import java.time.LocalDateTime;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinanceDto {
    private String id;
    private String accountNumber;
    private String customerName;
    private String transactionType;
    private LocalDateTime transactionDate;
    private Integer amount;
    private String description;

}
