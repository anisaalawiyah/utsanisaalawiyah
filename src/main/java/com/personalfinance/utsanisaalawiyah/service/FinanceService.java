package com.personalfinance.utsanisaalawiyah.service;

import java.time.LocalDateTime;
import java.util.List;

import  com.personalfinance.utsanisaalawiyah.dto.FinanceRequestDto;
import  com.personalfinance.utsanisaalawiyah.dto.FinanceResponeAnnualDto;
import  com.personalfinance.utsanisaalawiyah.dto.FinanceResponeDto;
import  com.personalfinance.utsanisaalawiyah.dto.FinanceResponeIncomeDto;
import  com.personalfinance.utsanisaalawiyah.dto.FinanceDto;

public interface FinanceService {
    String add(FinanceRequestDto dto);
    String remove(String id);
    String update(String id, FinanceRequestDto dto);
    List<FinanceDto> viewAll();

    FinanceResponeIncomeDto income(String account, LocalDateTime start, LocalDateTime end);
    FinanceResponeAnnualDto daily(String account);
    FinanceResponeAnnualDto monthly(String account);
    FinanceResponeAnnualDto annual(String account);
    FinanceResponeDto deviation(String account, LocalDateTime start, LocalDateTime end);
    List<FinanceResponeDto> viewAllDeviation(LocalDateTime start, LocalDateTime end);


}
