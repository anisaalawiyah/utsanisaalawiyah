package com.personalfinance.utsanisaalawiyah.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personalfinance.utsanisaalawiyah.dto.FinanceRequestDto;
import com.personalfinance.utsanisaalawiyah.dto.FinanceResponeAnnualDto;
import com.personalfinance.utsanisaalawiyah.dto.FinanceResponeDto;
import com.personalfinance.utsanisaalawiyah.dto.FinanceResponeIncomeDto;
import com.personalfinance.utsanisaalawiyah.dto.FinanceDto;
import com.personalfinance.utsanisaalawiyah.models.Finance;
import com.personalfinance.utsanisaalawiyah.repositories.FinanceRepository;


@Service
public class FinanceServicelmp implements FinanceService {

    @Autowired
    private FinanceRepository financeRepository;

    @Override
    public String add(FinanceRequestDto dto) {
        int a = 0;
        List<Finance> finances = financeRepository.findAll();
        Finance finance = new Finance();
        for (Finance prl : finances) {
            if (prl.getAccountNumber().equals(dto.getAccountNumber()) && prl.getName().equals(dto.getCustomerName())) {
                a = 0;
                break;
            }
            if (prl.getAccountNumber().equals(dto.getAccountNumber())) {
                a = 1;
                break;
            }
        }
        if (a == 0) {
            finance.setAccountNumber(dto.getAccountNumber());
            finance.setName(dto.getCustomerName());
            finance.setType(dto.getTransactionType());
            finance.setDate(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
            finance.setAmount(dto.getAmount());
            finance.setDescription(dto.getDescription());
            financeRepository.save(finance);
            return "Successfully";
        }
        if (a == 1) {
            return "Failed";
        }
        return "Failed";
    }

    
    @Override
    public String remove(String id) {
        Finance finance = financeRepository.findById(id).orElse(null);
        if (finance != null) {
            financeRepository.delete(finance);
            return "Successfully";
        } else {
            return "Id not Found";
        }
    }

    @Override
    public String update(String id, FinanceRequestDto dto) {
        Finance finance = financeRepository.findById(id).orElse(null);
        List<Finance> finances = financeRepository.findAll();
        int a = 0;
        for (Finance prl : finances) {
            if (prl.getAccountNumber().equals(dto.getAccountNumber()) && prl.getName().equals(dto.getCustomerName())) {
                a = 0;
                break;
            }
            if (prl.getAccountNumber().equals(dto.getAccountNumber())) {
                a = 1;
                break;
            }
        }
        if (a == 0) {
            finance.setAccountNumber(dto.getAccountNumber());
            finance.setName(dto.getCustomerName());
            finance.setType(dto.getTransactionType());
            finance.setDate(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
            finance.setAmount(dto.getAmount());
            finance.setDescription(dto.getDescription());
            financeRepository.save(finance);
            return "Successfully";
        }
        if (a == 1) {
            return "Failed";
        }
        return "Failed";

    }
    @Override
    public List<FinanceDto> viewAll() {
        return financeRepository.findAll().stream().map(this::toFinanceDto).toList();
    }

    public FinanceDto toFinanceDto(Finance finance) {
        return FinanceDto.builder().id(finance.getId()).accountNumber(finance.getAccountNumber())
                .customerName(finance.getName()).transactionType(finance.getType())
                .transactionDate(finance.getDate())
                .amount(finance.getAmount())
                .description(finance.getDescription()).build();
    }


    @Override
    public FinanceResponeIncomeDto income(String account, LocalDateTime start, LocalDateTime end) {
        int income = 0, expense = 0;
        Finance finance = financeRepository.findByAccountNumberUsingHql(account, start, end).get(0);
        List<Finance> finances = financeRepository.findByAccountNumberUsingHql(account, start, end);
        for (Finance prl : finances) {
            if (prl.getAccountNumber().equals(finance.getAccountNumber())) {
                if (prl.getType().equals("Credit")) {
                    income += prl.getAmount();
                }
                if (prl.getType().equals("Debit")) {
                    expense += prl.getAmount();
                }
            }

        }
        return FinanceResponeIncomeDto.builder().accountNumber(finance.getAccountNumber())
                .customerName(finance.getName())
                .totalExpense(expense).totalIncome(income).build();
    }

    @Override
    public FinanceResponeAnnualDto monthly(String account) {
        int income = 0, expense = 0;
        Finance finance = financeRepository.findByAccountNumber(account).get(0);

        LocalDateTime dateTime = LocalDateTime.now();
        Month month = dateTime.getMonth();
        String yearNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String monthName = month.toString();
        List<Finance> finances = financeRepository.findByAccountNumber(account);
        for (Finance prl : finances) {
            if (prl.getDate().getMonth().equals(month)) {
                if (prl.getType().equals("Credit")) {
                    income += prl.getAmount();
                }
                if (prl.getType().equals("Debit")) {
                    expense += prl.getAmount();
                }
            }

        }
        return FinanceResponeAnnualDto.builder().accountNumber(finance.getAccountNumber())
                .customerName(finance.getName())
                .period(monthName.concat(" " + yearNow)).totalExpense(expense).totalIncome(income).build();

    }
    @Override
    public FinanceResponeAnnualDto daily(String account) {
        int income = 0, expense = 0;

        Finance finance = financeRepository.findByAccountNumber(account).get(0);
        LocalDateTime dateTime = LocalDateTime.now();
        DayOfWeek day = dateTime.getDayOfWeek();
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String dayName = day.toString();
        List<Finance> finances = financeRepository.findByAccountNumber(account);
        for (Finance prl : finances) {
            if (prl.getDate().getDayOfWeek().equals(day)) {
                if (prl.getType().equals("Credit")) {
                    income += prl.getAmount();
                }
                if (prl.getType().equals("Debit")) {
                    expense += prl.getAmount();
                }
            }

        }
        return FinanceResponeAnnualDto.builder().accountNumber(finance.getAccountNumber())
                .customerName(finance.getName())
                .period(dayName.concat(", " + dateNow)).totalExpense(expense).totalIncome(income).build();

    }


    @Override
    public FinanceResponeAnnualDto annual(String account) {
        Finance finance = financeRepository.findByAccountNumber(account).get(0);
        int income = 0, expense = 0;
        String yearNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        List<Finance> finances = financeRepository.findByAccountNumber(account);
        for (Finance prl : finances) {
            if (prl.getDate().getYear() == LocalDateTime.now().getYear()) {
                if (prl.getType().equals("Credit")) {
                    income += prl.getAmount();
                }
                if (prl.getType().equals("Debit")) {
                    expense += prl.getAmount();
                }
            }

        }
        return FinanceResponeAnnualDto.builder().accountNumber(finance.getAccountNumber())
                .customerName(finance.getName())
                .period(yearNow).totalExpense(expense).totalIncome(income).build();

    }

    @Override
    public FinanceResponeDto deviation(String account, LocalDateTime start, LocalDateTime end) {
        int income = 0, expense = 0,deviation;

        Finance finance = financeRepository.findByAccountNumberUsingHql(account, start, end).get(0);
        List<Finance> finances = financeRepository.findByAccountNumberUsingHql(account, start, end);
        for (Finance prl : finances) {
            if (prl.getAccountNumber().equals(finance.getAccountNumber())) {
                if (prl.getType().equals("Credit")) {
                    income += prl.getAmount();
                }
                if (prl.getType().equals("Debit")) {
                    expense += prl.getAmount();
                }
            }
        }
        deviation = income - expense;
        String conclusion = deviation > 0 ? "Good" : deviation == 0 ? "Warning" : "Danger";
        return FinanceResponeDto.builder().accountNumber(finance.getAccountNumber()).customerName(finance.getName())
                .totalExpense(expense).totalIncome(income).deviation(deviation).conclusion(conclusion).build();
    }

    @Override
    public List<FinanceResponeDto> viewAllDeviation(LocalDateTime start, LocalDateTime end) {
        List<String> allDataAccount = financeRepository.findAllGroupByAccountNumber();
        List<FinanceResponeDto> allDataDeviation = new ArrayList<FinanceResponeDto>();
        for (String data : allDataAccount) {
            int income = 0;
            int expense = 0;
            int deviation;
            List<Finance> finances = financeRepository.findByAccountNumberUsingHql(data, start, end);
            for (Finance prl : finances) {
                if (prl.getAccountNumber().equals(data)) {
                    if (prl.getType().equals("Credit")) {
                        income += prl.getAmount();
                    }
                    if (prl.getType().equals("Debit")) {
                        expense += prl.getAmount();
                    }
                }
            }
            deviation = income - expense;
            String conclusion = deviation > 0 ? "Good" : deviation == 0 ? "Warning" : "Danger";
            allDataDeviation.add(FinanceResponeDto.builder().accountNumber(finances.get(0).getAccountNumber())
                    .customerName(finances.get(0).getName())
                    .totalExpense(expense).totalIncome(income).deviation(deviation).conclusion(conclusion).build());
        }
        return allDataDeviation;
    }


   

}
