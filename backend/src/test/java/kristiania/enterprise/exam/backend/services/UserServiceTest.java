package kristiania.enterprise.exam.backend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private static final AtomicInteger counter = new AtomicInteger(0);

    /*
        Note: by using unique ids here, I do not need to care about
        cleaning the database at each test
     */
    private String getUniqueEmail(){
        return "foo_UserServiceTest_" + counter.getAndIncrement() + "@test.com";
    }

    @Test
    public void testCanCreateAUser(){

        String email = getUniqueEmail();
        String givenName = "Sam";
        String familyName = "Gamgee";
        String password = "plants-and-rosie";


        boolean created = userService.createUser(email,givenName, familyName, password);
        assertTrue(created);
    }


    @Test
    public void testNoTwoUsersWithSameId(){

        String email = getUniqueEmail();

        boolean created = userService.createUser(email,"a-given", "a-fam", "a-pass");
        assertTrue(created);

        created = userService.createUser(email,"b-given", "b-fam", "b-pass");
        assertFalse(created);
    }
}
