package vn.com.multiplechoice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.javafaker.Faker;

import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.Gender;
import vn.com.multiplechoice.dao.model.enums.UserRole;
import vn.com.multiplechoice.dao.model.enums.UserStatus;

@SpringBootTest
class MultipleChoiceApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(MultipleChoiceApplicationTests.class);

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        Faker faker = new Faker(new Locale("vi_VN"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 300; i++) {
            try {
                User user = new User();

                user.setFirstname(faker.name().firstName());
                user.setUsername(faker.name().username());
                user.setLastname(faker.name().lastName());
                user.setEmail(faker.internet().emailAddress());
                user.setPassword(passwordEncoder.encode("123456"));
                user.setCreateTime(LocalDateTime.now());
                user.setPhoneNumber(faker.phoneNumber().phoneNumber());
                user.setGender(randomGender());
                user.setBirthday(LocalDate.of(1990,11,12));
                user.setRole(randomUserRole());
                user.setStatus(randomUserStatus());

                userService.save(user);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        assertTrue(userService.findAll().size() > 10);
    }

    private UserRole randomUserRole() {
        int number = getRandomNumberInRange(0, 2);
        UserRole role = UserRole.USER;

        if (number == 0) {
            role = UserRole.ADMIN;
        } else if (number == 1) {
            role = UserRole.INSPECTOR;
        } else if (number == 2) {
            role = UserRole.CREATOR;
        } else {
            role = UserRole.USER;
        }

        return role;
    }

    private Gender randomGender() {
        int number = getRandomNumberInRange(0, 1);

        return ( (number == 0) ? Gender.FEMALE : Gender.MALE);
    }

    
    private UserStatus randomUserStatus() {
        int number = getRandomNumberInRange(0, 1);

        return ( (number == 0) ? UserStatus.IN_ACTIVE : UserStatus.ACTIVE);
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
