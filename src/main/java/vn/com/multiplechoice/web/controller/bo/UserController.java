package vn.com.multiplechoice.web.controller.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private static final String BO_USER_LIST = "bo/user-list";

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;

    @GetMapping(value = { "", "/list" })
    public String getUsers(Model model) {
        List<User> users = userService.findAll();
        List<UserDto> userDtos = userConverter.toDto(users);
        model.addAttribute("users", userDtos);

        return BO_USER_LIST;
    }

    @GetMapping("/waiting-list")
    public String waitingUsers(Model model) {
        return "bo/user-waiting-list";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
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

        return "bo/user";
    }

    @PostMapping("/update")
    public String update(Model model, UserDto dto) {
        log.info("Admin update user by username : {}", dto.getUsername());
        User user = userService.findByUsername(dto.getUsername());
        userConverter.updateUser(user, dto);

        userService.save(user);

        return BO_USER_LIST;
    }

    @PostMapping("/save")
    public String save(Model model, UserDto dto) {
        log.info("Admin create user by username : {}", dto.getUsername());
        User user = userConverter.toNewEntity(dto);

        userService.save(user);

        return BO_USER_LIST;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        log.info("Delete user by id= {}", id);
        try {
            userService.deleteById(id);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
