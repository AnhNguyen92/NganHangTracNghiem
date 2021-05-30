package vn.com.multiplechoice.business.converter;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.dto.UserDto;

@Component
public class UserConverterService {
    @Autowired
    private ModelMapper modelMapper;

    public User toEntity(UserDto dto) {

        User user = modelMapper.map(dto, User.class);
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        
        
        return user;
    }

    public UserDto toDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
