package vn.com.multiplechoice.web.controller.bo;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.multiplechoice.business.converter.UserConverter;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.paging.Page;
import vn.com.multiplechoice.dao.model.paging.PagingRequest;
import vn.com.multiplechoice.web.dto.UserDto;

@RestController
@RequestMapping("/bo/users")
public class UserRestController {

    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserConverter userConverter;

    @PostMapping(value = "/ajax/list")
    public Page<User> list(@RequestBody PagingRequest pagingRequest) {
        Page<User> pageUser = userService.searchDataTable(pagingRequest);
        Page<UserDto> pageUserDTO = new Page<>();
        if (pageUser.getData() != null) {            
            pageUserDTO.setData(userConverter.toDto(pageUser.getData()));
        }
        
        return pageUser;
    }
    
    @PostMapping(value = "/ajax/waiting-list")
    public Page<User> waitingList(@RequestBody PagingRequest pagingRequest) {
        Page<User> pageUser = userService.searchWaitingList(pagingRequest);
        Page<UserDto> pageUserDTO = new Page<>();
        if (pageUser.getData() != null) {            
            pageUserDTO.setData(userConverter.toDto(pageUser.getData()));
        }
        
        return pageUser;
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
