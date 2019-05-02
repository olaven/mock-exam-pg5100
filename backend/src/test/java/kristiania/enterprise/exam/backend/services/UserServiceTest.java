package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Booking;
import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.entity.UserEntity;
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

        int countBefore = userService.getBookedTrips(userEmail).size();
        userService.bookTrip(userEmail, tripId);
        List<Trip> bookedTrips = userService.getBookedTrips(userEmail);


        boolean found = bookedTrips.stream()
                .filter(trip-> trip.getId().equals(tripId))
                .findFirst()
                .isPresent();
        boolean increased = (bookedTrips.size() == countBefore + 1);


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

        String userEmail = persistUser();

        assertThrows(Exception.class, () -> {
            userService.bookTrip(userEmail,-1l);
        });
    }

    @Test
    public void canCheckIfUserHasBooked() {

        String userEmail = persistUser();
        Long tripId = persistDefaultTrip();

        userService.bookTrip(userEmail, tripId);
        boolean booked = userService.userHasBooked(userEmail, tripId);
        assertTrue(booked);
    }

    @Test
    public void canCheckIfUserHasNotBooked() {

        String userEmail = persistUser();
        Long tripId = persistDefaultTrip();

        //NOTE: never booked
        boolean booked = userService.userHasBooked(userEmail, tripId);
        assertFalse(booked);
    }

    @Test
    public void canRetrieveUser() {

        String userEmail = persistUser();
        UserEntity user = userService.getUser(userEmail);

        assertEquals(userEmail, user.getEmail());
    }
}
