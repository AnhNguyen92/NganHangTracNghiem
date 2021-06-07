package vn.com.multiplechoice.web.controller.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.UserRole;
import vn.com.multiplechoice.dao.model.enums.UserStatus;

@Controller
@RequestMapping("/bo/sbadmin")
public class SbAdminController {
    
    @GetMapping
    public String main() {
        return "bo/employee";
    }
    
    @GetMapping("/tables")
    public String tables(Model model) {
        model.addAttribute("users", getAllUser());
        return "bo/tables";
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
            user.setEmail(uuidAsString + (i + 21) + "@gmail.com");
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
