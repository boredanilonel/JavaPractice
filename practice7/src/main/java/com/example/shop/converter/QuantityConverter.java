package com.example.shop.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.example.shop.model.Quantity;

@Converter(autoApply = true)
public class QuantityConverter implements AttributeConverter<Quantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Quantity attribute) {
        return (attribute != null) ? attribute.getValue() : null;
    }

    @Override
    public Quantity convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? new Quantity(dbData) : null;
    }
}
