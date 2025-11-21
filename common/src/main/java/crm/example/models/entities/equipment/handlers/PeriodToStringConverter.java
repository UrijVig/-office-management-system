package crm.example.models.entities.equipment.handlers;

import java.time.Period;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PeriodToStringConverter implements AttributeConverter<Period, String>{

    @Override
    public String convertToDatabaseColumn(Period attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public Period convertToEntityAttribute(String dbData) {
        return dbData != null ? Period.parse(dbData) : null;
    }
    

}
