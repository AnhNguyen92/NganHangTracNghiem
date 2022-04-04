package vn.com.multiplechoice.dao.model.enums.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import vn.com.multiplechoice.dao.model.enums.Gender;


@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return (gender == null) ? null : gender.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(Gender.values()).filter(c -> c.getValue().equals(value)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
