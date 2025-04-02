package com.example.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.example.shop.model.Payment;

@Entity
@Getter
@Setter
@Table(name = "check_payment")
@PrimaryKeyJoinColumn(name = "payment_id")
public class Check extends Payment {
    private String name;

    @Column(name = "bank_id")
    private String bankID;
}
