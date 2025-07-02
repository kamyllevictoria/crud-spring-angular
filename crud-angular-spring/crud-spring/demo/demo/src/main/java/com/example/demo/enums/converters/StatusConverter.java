package com.example.demo.enums.converters;

import com.example.demo.enums.Status;
import jakarta.persistence.AttributeConverter;
import java.util.stream.Stream;

public class StatusConverter implements AttributeConverter<Status, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Status status) {
        if(status == null){
            return null;
        }
        return status.getValueStatus();
    }

    @Override
    public Status convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }
        return Status.fromValue(dbData);
    }
}
