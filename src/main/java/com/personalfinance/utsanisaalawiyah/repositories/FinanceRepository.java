package com.personalfinance.utsanisaalawiyah.repositories;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.personalfinance.utsanisaalawiyah.models.Finance;

public interface FinanceRepository extends JpaRepository<Finance, String> {

    @Query("SELECT f FROM Finance f WHERE f.accountNumber = :account AND date BETWEEN :start AND :end ")
    List<Finance> findByAccountNumberUsingHql(String account , LocalDateTime start , LocalDateTime end);

    @Query(value = "select * from personal_transaction_log where account_number = :account", nativeQuery = true)
    List<Finance> findByAccountNumber(String account);

    @Query(value = " SELECT account_number FROM personal_transaction_log  GROUP BY account_number ASC", nativeQuery = true)
    List<String> findAllGroupByAccountNumber();


}