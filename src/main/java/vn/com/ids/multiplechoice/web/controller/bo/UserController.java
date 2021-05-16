package vn.com.ids.multiplechoice.web.controller.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import vn.com.ids.multiplechoice.dao.enums.UserRole;
import vn.com.ids.multiplechoice.dao.model.User;
import vn.com.ids.multiplechoice.dao.model.enums.UserStatus;
import vn.com.ids.multiplechoice.web.dto.UserDto;

@RestController
@RequestMapping("/bo/user")
public class UserController {

    @GetMapping(value = { "", "/list" })
    public ModelAndView getAllUsers() {
        ModelAndView mav = new ModelAndView("bo/user-list");
        mav.addObject("users", getAllUser());

        return mav;
    }

    @GetMapping("/waitingList")
    public ModelAndView waitingUsers() {
        ModelAndView mav = new ModelAndView("bo/user-waiting-list");
        List<User> waitingUsers = getWaitingUsers();

        mav.addObject("users", waitingUsers);

        return mav;
    }

    @GetMapping("")
    public ModelAndView addNew() {
        ModelAndView mav = new ModelAndView("bo/add-user");

        mav.addObject("users", new UserDto());

        return mav;
    }

    @PostMapping("")
    public ModelAndView save(UserDto dto) {
        ModelAndView mav = new ModelAndView("bo/user-list");

        mav.addObject("users", new UserDto());

        return mav;
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
            user.setEmail("user" + (i + 1) + "@gmail.com");
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
            user.setEmail("user" + (i + 21) + "@gmail.com");
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
            role = UserRole.SUPERVISOR;
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
