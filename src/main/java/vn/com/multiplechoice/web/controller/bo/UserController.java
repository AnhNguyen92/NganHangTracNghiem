package vn.com.multiplechoice.web.controller.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.UserConverterService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.dto.UserDto;

@Controller
@RequestMapping("/bo/user")
public class UserController {

    private static final String BO_USER_LIST = "bo/user-list";

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserConverterService userConverter;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"", "/list"})
    public String getUsers(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( !(principal instanceof UserDetails) ) {
            return "bo/login";
        }
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        
        return BO_USER_LIST;
    }

    @GetMapping("/waiting-list")
    public String waitingUsers(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( !(principal instanceof UserDetails) ) {
            return "bo/login";
        }
        List<User> waitingUsers = userService.getWaitingUsers();

        model.addAttribute("users", waitingUsers);

        return "bo/user-waiting-list";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( !(principal instanceof UserDetails) ) {
            return "bo/login";
        }
        // for test only
//        User user = userService.findOne(8l);
        User user = userService.findOne(id);
        if (user == null) {
            return "bo/error/404";
        }
        model.addAttribute("user", userConverter.toDto(user));
        return "bo/user";
    }

    @GetMapping("/new")
    public String addNew(Model model) {
        model.addAttribute("user", new UserDto());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( !(principal instanceof UserDetails) ) {
            return "bo/login";
        }
        
        return "bo/user";
    }

    @PostMapping("update")
    public String update(Model model, UserDto dto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( !(principal instanceof UserDetails) ) {
            return "bo/login";
        }
        log.info("Admin update user by username : {}", dto.getUsername());
        User user = userService.findByUsername(dto.getUsername());
        userConverter.updateUser(user, dto);

        userService.save(user);

        return BO_USER_LIST;
    }
    
    @PostMapping("/save")
    public String save(Model model, UserDto dto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( !(principal instanceof UserDetails) ) {
            return "bo/login";
        }
        log.info("Admin create user by username : {}", dto.getUsername());
        User user = userConverter.toNewEntity(dto);

        userService.save(user);

        return BO_USER_LIST;
    }

}
