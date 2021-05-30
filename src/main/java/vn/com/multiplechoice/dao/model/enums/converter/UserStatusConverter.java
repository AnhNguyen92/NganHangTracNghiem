package vn.com.multiplechoice.dao.model.enums.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import vn.com.multiplechoice.dao.model.enums.UserStatus;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
    @Override
    public String convertToDatabaseColumn(UserStatus status) {
        return (status == null) ? null : status.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(UserStatus.values()).filter(c -> c.getValue().equals(value)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
