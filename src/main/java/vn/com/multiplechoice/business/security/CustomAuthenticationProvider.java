package vn.com.multiplechoice.business.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public Authentication authenticate(Authentication auth) {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        User user = userService.findByUsername(username);
        if (user == null || !bCryptPasswordEncoder.encode(password).equals(user.getPassword())) {
            throw new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không đúng");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
        
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
