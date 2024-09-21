package com.personalfinance.utsanisaalawiyah.models;



import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personal_transaction_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Finance {
    @Id
    @UuidGenerator
    @Column(name = "id",length = 36,nullable = false)
    private String id;

    @Column(name = "account_number",length = 15,nullable = false)
    private String accountNumber;

    @Column(name = "customer_name",length = 100,nullable = false)
    private String name;

    @Column(name = "transaction_type",length = 10)
    private String type;

    @Column(name = "transaction_date")
    private LocalDateTime date;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "description",length = 500)
    private String description;

}
