package vn.com.ids.multiplechoice.web.controller.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.ids.multiplechoice.dao.model.User;

@RestController
@RequestMapping("/bo/user")
public class UserController {
    
    @GetMapping(value= {"", "/list"})
    public String getAllUsers(Model model) {
        List<User> users = getAllUser();
        model.addAttribute("users", users);
        
        return "bo/userList";
    }
    
    @GetMapping("/waitingList")
    public String getWaitingUsers(Model model) {
        List<User> users = getWaitingUsers();
        
        model.addAttribute("users", users);
        
        return "bo/userWaitingList";
    }
    
    private List<User> getAllUser() {
        List<User> users = new ArrayList<User>(){
            private static final long serialVersionUID = 1L;
        {
            addAll(getActiveUser());
            addAll(getWaitingUsers());
              }};
        return users;
    }
    
    private List<User> getActiveUser() {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setEmail("user" + (i+1) + "@gmail.com");
            user.setPhone("0123456789");
            user.setRole(randomUserRole());
            user.setUsername("user" + (i+1));
            user.setPassword("123456");
            user.setActive(true);
            
            users.add(user);
        }
        
        return users;
    }
    
    private List<User> getWaitingUsers() {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setEmail("user" + (i+21) + "@gmail.com");
            user.setPhone("0123456789");
            user.setRole(randomUserRole());
            user.setUsername("user" + (i+21));
            user.setPassword("123456");
            user.setActive(false);
            
            users.add(user);
        }
        
        return users;
    }
    
    private String randomUserRole() {
        Random random = new Random();
        int number = random.nextInt(2);
        
        return (number == 0 ? "Admin" : (number == 1 ? "Super-User" : "User"));
    }
}
