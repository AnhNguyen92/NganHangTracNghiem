package vn.com.multiplechoice.business.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.comparators.UserComparators;
import vn.com.multiplechoice.dao.model.paging.Column;
import vn.com.multiplechoice.dao.model.paging.Order;
import vn.com.multiplechoice.dao.model.paging.Page;
import vn.com.multiplechoice.dao.model.paging.PagingRequest;
import vn.com.multiplechoice.dao.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User, Long> implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final Comparator<User> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void resetNewPassword(User user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public Page<User> searchDataTable(PagingRequest pagingRequest) {
        List<User> users = userRepository.findAll();

        return getPage(users, pagingRequest);
    }

    private Page<User> getPage(List<User> users, PagingRequest pagingRequest) {
        List<User> filtered = users.stream().sorted(sortUsers(pagingRequest)).filter(filterUsers(pagingRequest)).skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength()).collect(Collectors.toList());

        long count = users.stream().filter(filterUsers(pagingRequest)).count();

        Page<User> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<User> filterUsers(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch().getValue())) {
            return employee -> true;
        }
        String value = pagingRequest.getSearch().getValue();

        return user -> user.getUsername().toLowerCase().contains(value) || user.getEmail().toLowerCase().contains(value);
    }

    private Comparator<User> sortUsers(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }
        try {
            Order order = pagingRequest.getOrder().get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);
            Comparator<User> comparator = UserComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }

    @Override
    public Page<User> searchWaitingList(PagingRequest pagingRequest) {
        List<User> users = userRepository.getWaitingUsers();

        return getPage(users, pagingRequest);
    }

}
