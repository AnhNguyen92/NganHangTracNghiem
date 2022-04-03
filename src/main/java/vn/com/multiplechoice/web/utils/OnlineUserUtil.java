package vn.com.multiplechoice.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;

@Component
public class OnlineUserUtil {

    @Autowired
    private UserService userService;

    public User getOnlineUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }

    public Long getOnlineUserID() {
        User onlineUser = getOnlineUser();
        return onlineUser.getId();
    }
    
}
