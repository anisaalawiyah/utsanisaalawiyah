package com.personalfinance.utsanisaalawiyah.controllers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personalfinance.utsanisaalawiyah.dto.FinanceRequestDto;
import com.personalfinance.utsanisaalawiyah.dto.FinanceResponeAnnualDto;
import com.personalfinance.utsanisaalawiyah.dto.FinanceResponeDto;
import com.personalfinance.utsanisaalawiyah.dto.FinanceResponeIncomeDto;
import com.personalfinance.utsanisaalawiyah.dto.FinanceDto;
import com.personalfinance.utsanisaalawiyah.service.FinanceService;

@RestController
@RequestMapping("/personal-transaction")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @PostMapping("/")
    public String add(@RequestBody FinanceRequestDto dto) {
        return financeService.add(dto);
    }

    @GetMapping("/")
    public List<FinanceDto> view() {
        return financeService.viewAll();
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable String id) {
        return financeService.remove(id);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody FinanceRequestDto dto) {
        return financeService.update(id, dto);
    }

    @GetMapping("persoanal-expense-income")
    public FinanceResponeIncomeDto income(@RequestParam String account, LocalDate startDate, LocalDate endDate) {
        LocalDateTime atStart = startDate.atStartOfDay();
        LocalDateTime atEnd = endDate.atStartOfDay();
        return financeService.income(account, atStart, atEnd);
    }

    @GetMapping("personal-expense-income-daily")
    public FinanceResponeAnnualDto daily(@RequestParam String account) {
        return financeService.daily(account);
    }

    @GetMapping("personal-expense-income-monthly")
    public FinanceResponeAnnualDto month(@RequestParam String account) {
        return financeService.monthly(account);
    }

    @GetMapping("personal-expense-income-annual")
    public FinanceResponeAnnualDto annual(@RequestParam String account) {
        return financeService.annual(account);
    }

    @GetMapping("personal-deviation")
    public FinanceResponeDto deviation(@RequestParam String account, LocalDate startDate, LocalDate endDate) {
        LocalDateTime atStart = startDate.atStartOfDay();
        LocalDateTime atEnd = endDate.atStartOfDay();
        return financeService.deviation(account, atStart, atEnd);
    }

    @GetMapping("personal-deviation-allcustomer")
    public List<FinanceResponeDto> viewAllDeviation(LocalDate startDate, LocalDate endDate) {
        LocalDateTime atStart = startDate.atStartOfDay();
        LocalDateTime atEnd = endDate.atStartOfDay();
        return financeService.viewAllDeviation(atStart,atEnd);
    }



}

