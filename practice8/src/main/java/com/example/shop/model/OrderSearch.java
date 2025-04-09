package com.example.shop.model;

import lombok.Data;
import com.example.shop.model.Payment;
import com.example.shop.model.PaymentStatus;

import java.time.LocalDateTime;

@Data
public class OrderSearch {
    private Address address;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Class<? extends Payment> payment;
    private String customerName;
    private PaymentStatus paymentStatus;
    private String orderStatus;
}
