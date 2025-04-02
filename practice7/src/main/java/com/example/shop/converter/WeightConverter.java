package com.example.shop.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.example.shop.model.Weight;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class WeightConverter implements AttributeConverter<Weight, Double> {

    @Override
    public Double convertToDatabaseColumn(Weight attribute) {
        return (attribute != null) ? attribute.getValue().doubleValue() : null;
    }

    @Override
    public Weight convertToEntityAttribute(Double dbData) {
        return (dbData != null) ? new Weight(BigDecimal.valueOf(dbData)) : null;
    }
}