package com.personalfinance.utsanisaalawiyah.dto;
import lombok.*;
import jakarta.annotation.Nullable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Nullable
public class FinanceResponeIncomeDto {
    private String accountNumber;
    private String customerName;
    private Integer totalExpense;
    private Integer totalIncome;

}
