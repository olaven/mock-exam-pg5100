package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.BookingEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest extends ServiceTestBase {

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

    @Test
    public void testCanBookTrip() {

        String userEmail = persistUser();
        Long tripId = persistDefaultTrip();

        int countBefore = userService.getBookings(userEmail).size();
        Long bookingId = userService.bookTrip(userEmail, tripId);
        List<BookingEntity> bookings = userService.getBookings(userEmail);


        boolean found = bookings.stream()
                .filter(booking-> booking.getId().equals(bookingId))
                .findFirst()
                .isPresent();
        boolean increased = (bookings.size() == countBefore + 1);


        assertTrue(found);
        assertTrue(increased);

    }

    @Test
    public void testThrowsOnInvalidUserEmail() {

        Long tripId = persistDefaultTrip();

        assertThrows(Exception.class, () -> {
            userService.bookTrip("not@registered.com", tripId);
        });
    }

    @Test
    public void testThrowsOnInvalidTripId() {

        Long tripId = persistDefaultTrip();
        String userEmail = persistUser();

        assertThrows(Exception.class, () -> {
            userService.bookTrip(userEmail,-1l);
        });
    }
}
