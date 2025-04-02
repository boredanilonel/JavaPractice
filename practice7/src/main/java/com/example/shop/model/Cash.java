package com.example.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.example.shop.model.Payment;

@Entity
@Getter
@Setter
@Table(name = "cash_payment")
@PrimaryKeyJoinColumn(name = "payment_id")
public class Cash extends Payment {
    private float cashTendered;
}