//package vn.com.multiplechoice.dao.model.enums.converter;
//
//import java.util.stream.Stream;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//
//import vn.com.multiplechoice.dao.model.enums.UserRole;
//
//@Converter(autoApply = true)
//public class UserRoleConverter implements AttributeConverter<UserRole, String> {
//    @Override
//    public String convertToDatabaseColumn(UserRole role) {
//        return (role == null) ? null : role.getValue();
//    }
//
//    @Override
//    public UserRole convertToEntityAttribute(String value) {
//        if (value == null) {
//            return null;
//        }
//        return Stream.of(UserRole.values()).filter(c -> c.getValue().equals(value)).findFirst().orElseThrow(IllegalArgumentException::new);
//    }
//
//}
