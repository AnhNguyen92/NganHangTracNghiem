package vn.com.multiplechoice.web.controller.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.multiplechoice.business.converter.UserConverter;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.paging.datatable.Page;
import vn.com.multiplechoice.dao.model.paging.datatable.PagingRequest;
import vn.com.multiplechoice.web.dto.UserDto;

@RestController
@RequestMapping("/bo/ajax/users")
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> list(@RequestBody PagingRequest pagingRequest) {
        log.info("start get list user by ajax");
        Page<User> pageUser = userService.searchDataTable(pagingRequest);
        Page<UserDto> pageUserDTO = new Page<>();
        if (pageUser.getData() != null) {
            pageUserDTO.setData(userConverter.toDto(pageUser.getData()));
        }

        return pageUser;
    }

    @PostMapping(value = "/waiting-list")
    public Page<User> waitingList(@RequestBody PagingRequest pagingRequest) {
        log.info("start get waitingList user by ajax");
        Page<User> pageUser = userService.searchWaitingList(pagingRequest);
        Page<UserDto> pageUserDTO = new Page<>();
        if (pageUser.getData() != null) {
            pageUserDTO.setData(userConverter.toDto(pageUser.getData()));
        }

        return pageUser;
    }

}
