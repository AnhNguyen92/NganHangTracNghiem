package vn.com.multiplechoice.business.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.javafaker.Faker;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.comparators.UserComparators;
import vn.com.multiplechoice.dao.model.enums.UserRole;
import vn.com.multiplechoice.dao.model.enums.UserStatus;
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
//            List<User> users = userRepository.findAll();
        List<User> users = getAllUser();

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

    private List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        users.addAll(getFakeActiveUser());
        users.addAll(getFakeWaitingUsers());

        return users;
    }

    private List<User> getFakeActiveUser() {
        List<User> users = new ArrayList<>();
        Faker faker = new Faker(new Locale("vi_VN"));
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setId(i + 1l);
            
            user.setUsername(faker.name().username());
            user.setEmail(faker.internet().emailAddress());
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setRole(randomUserRole());
            user.setFirstname(faker.name().firstName());
            user.setLastname(faker.name().lastName());
            user.setPassword("123456");
            user.setStatus(UserStatus.ACTIVE);

            users.add(user);
        }

        return users;
    }

    private List<User> getFakeWaitingUsers() {
        List<User> users = new ArrayList<>();
        Faker faker = new Faker(new Locale("vi_VN"));
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setId(i + 31l);
            user.setUsername(faker.name().username());
            user.setEmail(faker.internet().emailAddress());
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setRole(randomUserRole());
            user.setFirstname(faker.name().firstName());
            user.setLastname(faker.name().lastName());
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

        Random r = null;
        try {
            r = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public List<User> getWaitingUsers() {
        return userRepository.getWaitingUsers();
    }

}
