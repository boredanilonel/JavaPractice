package com.example.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.example.shop.model.Payment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "credit_payment")
@PrimaryKeyJoinColumn(name = "payment_id")
public class Credit extends Payment {
    private String number;
    private String type;
    private LocalDateTime expDate;
}
