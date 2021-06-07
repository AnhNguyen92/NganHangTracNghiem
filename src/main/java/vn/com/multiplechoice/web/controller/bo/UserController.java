package vn.com.multiplechoice.web.controller.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.UserConverterService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.UserRole;
import vn.com.multiplechoice.dao.model.enums.UserStatus;
import vn.com.multiplechoice.web.dto.UserDto;

@Controller
@RequestMapping("/bo/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserConverterService userConverter;

    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public String getAllUsers(Model model) {
        model.addAttribute("users", getAllUser());

        return "bo/user-list";
    }

    @GetMapping(value = "/list")
    public String getUsers(Model model) {
        return "bo/user-list";
    }

    @GetMapping("/waiting-list")
    public String waitingUsers(Model model) {
        List<User> waitingUsers = getWaitingUsers();

        model.addAttribute("users", waitingUsers);

        return "bo/user-waiting-list";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        // for test only
        User user = userService.findOne(4l);
//        User user = userService.findOne(id);
        model.addAttribute("user", userConverter.toDto(user));
        return "bo/user";
    }

    @GetMapping("/new")
    public String addNew(Model model) {
        model.addAttribute("user", new UserDto());

        return "bo/user";
    }

    @PostMapping("/save")
    public String save(Model model, UserDto dto) {
        log.info("Admin create user by username : {}", dto.getUsername());
        model.addAttribute("user", new UserDto());
        User user = userConverter.toEntity(dto);

        userService.save(user);

        return "bo/user-list";
    }

    private List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        users.addAll(getActiveUser());
        users.addAll(getWaitingUsers());

        return users;
    }

    private List<User> getActiveUser() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setId(i + 1l);
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            user.setUsername(uuidAsString);
            user.setEmail(uuidAsString + (i + 1) + "@gmail.com");
            user.setPhoneNumber("0123456789");
            user.setRole(randomUserRole());
            user.setFirstname("user" + (i + 1));
            user.setPassword("123456");
            user.setStatus(UserStatus.ACTIVE);

            users.add(user);
        }

        return users;
    }

    private List<User> getWaitingUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setId(i + 21l);
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            user.setUsername(uuidAsString);
            user.setEmail(uuidAsString + (i + 21) + "@gmail.com");
            user.setPhoneNumber("0123456789");
            user.setRole(randomUserRole());
            user.setFirstname("user" + (i + 21));
            user.setPassword("123456");
            user.setStatus(UserStatus.IN_ACTIVE);

            users.add(user);
        }

        return users;
    }

    private UserRole randomUserRole() {
        int number = getRandomNumberInRange(0, 2);
        UserRole role = UserRole.USER;

        if (number == 0) {
            role = UserRole.ADMIN;
        } else if (number == 1) {
            role = UserRole.INSPECTOR;
        }

        return role;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
