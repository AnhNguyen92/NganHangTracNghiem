package vn.com.multiplechoice.web.controller.bo;

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
import vn.com.multiplechoice.web.dto.UserDto;

@Controller
@RequestMapping("/bo/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserConverterService userConverter;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"", "/list"})
    public String getUsers(Model model) {
        return "bo/user-list";
    }

//    @GetMapping("/waiting-list")
//    public String waitingUsers(Model model) {
//        List<User> waitingUsers = getWaitingUsers();
//
//        model.addAttribute("users", waitingUsers);
//
//        return "bo/user-waiting-list";
//    }

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

}
