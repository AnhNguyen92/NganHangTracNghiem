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

    // use for normal user registration
    public User toNewEntity(SignUpForm signUpRequest) {
        User user = modelMapper.map(signUpRequest, User.class);
        user.setCreateTime(LocalDateTime.now());
        user.setStatus(UserStatus.IN_ACTIVE);
        user.setRole(UserRole.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return user;
    }

    public UserDto toDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    // use for admin
    public User toNewEntity(UserDto dto) {
        User user = modelMapper.map(dto, User.class);
        user.setCreateTime(LocalDateTime.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(UserRole.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return user;
    }
    
    public void updateUser(User model, UserDto dto) {
        if (dto.getBirthday() != null) {
            model.setBirthday(dto.getBirthday());
        }
        if (dto.getEmail() != null) {
            model.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && dto.getPassword().length() >= 6) {
            model.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
        if (dto.getFirstname() != null) {
            model.setFirstname(dto.getFirstname());
        }
        if (dto.getPhoneNumber() != null) {
            model.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getLastname() != null) {
            model.setLastname(dto.getLastname());
        }
        if (dto.getStatus() != null) {
            model.setStatus(dto.getStatus());
        }
        if (dto.getGender() != null) {
            model.setGender(dto.getGender());
        }
        if (dto.getRole() != null) {
            model.setRole(dto.getRole());
        }
    }

}
