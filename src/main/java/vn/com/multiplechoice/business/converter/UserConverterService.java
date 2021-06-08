package vn.com.multiplechoice.business.converter;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.UserRole;
import vn.com.multiplechoice.dao.model.enums.UserStatus;
import vn.com.multiplechoice.web.dto.UserDto;
import vn.com.multiplechoice.web.form.SignUpForm;

@Service
public class UserConverterService {
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserConverterService(ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User toEntity(UserDto dto) {
        User user = modelMapper.map(dto, User.class);
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return user;
    }

    public User toEntity(SignUpForm signUpRequest) {
        User user = modelMapper.map(signUpRequest, User.class);
        user.setCreateTime(LocalDateTime.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setCreateTime(LocalDateTime.now());
        user.setRole(UserRole.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return user;
    }

    public UserDto toDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

}
