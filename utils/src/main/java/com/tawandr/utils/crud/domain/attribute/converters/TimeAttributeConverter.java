package com.tawandr.utils.crud.domain.attribute.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class TimeAttributeConverter implements AttributeConverter<LocalDateTime,  Timestamp> {
     
    @Override
    public  Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null :  Timestamp.valueOf(localDateTime);
    }


    @Override
    public LocalDateTime convertToEntityAttribute( Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}