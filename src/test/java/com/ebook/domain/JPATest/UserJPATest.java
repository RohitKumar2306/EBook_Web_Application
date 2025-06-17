package com.ebook.domain.JPATest;
import com.ebook.domain.User;
import com.ebook.domain.UserRole;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

public class UserJPATest extends AbstractJPATest {

    public static String generateRandomPhoneNumber() {
        long number = (long)(Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(number);
    }

    @Test
    public void createTest() {
        logger.info("Running createTest...");
        String uniqueName = "user" + UUID.randomUUID();
        String randomPhone = generateRandomPhoneNumber();

        User user = new User(uniqueName, uniqueName+"@email.com", "password456", randomPhone, "456 Oak St", UserRole.ROLE_LIBRARIAN);
        persistEntity(user);
        logger.info("User successfully persisted in createTest.");
        User readBackFromDatabaseForAssertion = findEntity(User.class,user.getId());
        assertNotNull(readBackFromDatabaseForAssertion);
        assertEquals(user.getId(), readBackFromDatabaseForAssertion.getId());
        logger.info("createTest completed successfully.");
    }

    @Test
    public void readTest() {
        logger.info("Running readTest...");
        String uniqueName = "user" + UUID.randomUUID();
        String randomPhone = generateRandomPhoneNumber();
        String email = uniqueName+"@email.com";
        User user = new User(uniqueName, email, "password456", randomPhone, "456 Oak St", UserRole.ROLE_LIBRARIAN);
        persistEntity(user);
        // Retrieve the user created in the beforeEach method
        User findUser = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).setParameter("email", email)
                .getSingleResult();

        assertNotNull(findUser);
        assertEquals(uniqueName, findUser.getName());
        logger.info("readTest completed successfully.");
    }

    @Test
    public void updateTest(){

        logger.info("Running updateTest...");
        String uniqueName = "user" + UUID.randomUUID();
        String randomPhone = generateRandomPhoneNumber();

        User user = new User(uniqueName, uniqueName+"@email.com", "password456", randomPhone, "456 Oak St", UserRole.ROLE_LIBRARIAN);
        persistEntity(user);
        String newAddress = "420 kansas st";
        tx.begin();
        user.setAddress(newAddress);
        tx.commit();
        logger.info("User address updated successfully in updateTest.");
        User updatedUser = findEntity(User.class,user.getId());
        assertEquals(newAddress,updatedUser.getAddress());
        logger.info("updateTest completed successfully.");
    }

    @Test
    public void deleteTest() {
        logger.info("Running deleteTest...");
        String uniqueName = "user" + UUID.randomUUID();
        String randomPhone = generateRandomPhoneNumber();

        User user = new User(uniqueName, uniqueName+"@email.com", "password456", randomPhone, "456 Oak St", UserRole.ROLE_LIBRARIAN);
        persistEntity(user);
        User findUser = findEntity(User.class, user.getId());
        assertNotNull(findUser);

        removeEntity(findUser);
        User deletedUser = findEntity(User.class, user.getId());
        assertNull(deletedUser);


    }
}
