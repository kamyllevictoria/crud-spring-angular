package com.example.demo.enums.converters;

import com.example.demo.enums.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if(status == null){
            return null;
        }
        return status.getStatus();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }
        return Status.fromString(dbData);
    }
}
