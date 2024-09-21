package com.personalfinance.utsanisaalawiyah.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinanceRequestDto {
    private String accountNumber;
    private String customerName;
    private String transactionType;
    private Integer amount;
    private String description;

}
