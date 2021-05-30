package vn.com.ids.multiplechoice.business.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.ids.multiplechoice.dao.model.User;
import vn.com.ids.multiplechoice.web.dto.UserDto;

@Component
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;
    
    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }
    
    public UserDto toDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }
    
}
