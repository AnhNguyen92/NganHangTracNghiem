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

import vn.com.multiplechoice.business.converter.UserConverter;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.dto.UserDto;

@Controller
@RequestMapping("/bo/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String BO_LOGIN = "bo/login";
    private static final String BO_USER_LIST = "bo/user-list";

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;

    @GetMapping(value = { "", "/list" })
    public String getUsers(Model model) {
        if (!isAuthenticatedAdminUser()) {
            return BO_LOGIN;
        }
        List<User> users = userService.findAll();
        List<UserDto> userDtos = userConverter.toDto(users);
        model.addAttribute("users", userDtos);

        return BO_USER_LIST;
    }

    @GetMapping("/waiting-list")
    public String waitingUsers(Model model) {
        if (!isAuthenticatedAdminUser()) {
            return BO_LOGIN;
        }

        return "bo/user-waiting-list";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        if (!isAuthenticatedAdminUser()) {
            return BO_LOGIN;
        }
        User user = userService.findOne(id);
        if (user == null) {
            return "bo/error/404";
        }
        model.addAttribute("user", userConverter.toDto(user));

        return "bo/user";
    }

    @GetMapping("/new")
    public String addNew(Model model) {
        if (!isAuthenticatedAdminUser()) {
            return BO_LOGIN;
        }
        model.addAttribute("user", new UserDto());

        return "bo/user";
    }

    @PostMapping("/update")
    public String update(Model model, UserDto dto) {
        if (!isAuthenticatedAdminUser()) {
            return BO_LOGIN;
        }
        log.info("Admin update user by username : {}", dto.getUsername());
        User user = userService.findByUsername(dto.getUsername());
        userConverter.updateUser(user, dto);

        userService.save(user);

        return BO_USER_LIST;
    }

    @PostMapping("/save")
    public String save(Model model, UserDto dto) {
        if (!isAuthenticatedAdminUser()) {
            return BO_LOGIN;
        }

        log.info("Admin create user by username : {}", dto.getUsername());
        User user = userConverter.toNewEntity(dto);

        userService.save(user);

        return BO_USER_LIST;
    }

    private boolean isAuthenticatedAdminUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails);
    }

}
