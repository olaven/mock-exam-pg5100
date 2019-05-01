package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.ResetService;
import kristiania.enterprise.exam.backend.Season;
import kristiania.enterprise.exam.backend.entity.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServiceTestBase {

    private static final AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    protected UserService userService;
    @Autowired
    protected TripService tripService;
    @Autowired
    private ResetService resetService;


    @BeforeEach
    public void init() {

        resetService.resetDatabase();
    }


    protected String persistUser() {

        String email = getUniqueEmail();
        String givenName = "test-given";
        String familyName = "test-family";
        String password = "test-password";


        userService.createUser(email,givenName, familyName, password);
        return email;
    }

    protected Long persistDefaultTrip() {


        String locationName = "default location name";
        int cost = new Random().nextInt(500);
        LocalDate date = LocalDate.now().plusMonths(1);

       return persistTrip(locationName, cost, date);
    }

    protected Long persistTripWithLocationName(String locationName) {

        int cost = new Random().nextInt(500);
        LocalDate date = LocalDate.now().plusMonths(1);

        return persistTrip(locationName, cost, date);
    }

    protected Long persistTripWithCost(int cost) {

        String locationName = "default location name";
        LocalDate date = LocalDate.now().plusMonths(1);

        return persistTrip(locationName, cost, date);
    }

    protected Long persistTrip(String locationName, int cost, LocalDate date) {

        Location location = new Location();

        location.setName(locationName);
        location.setDescription("A nice testing ground for new features");
        location.setTrips(new ArrayList<>());
        Season season = Season.AUTUMN;


        Long id = tripService.createTrip("Test location", "Default test description", cost, location, season, date);
        return id;
    }


    /*
        Note: by using unique ids here, I do not need to care about
        cleaning the database at each test
     */
    protected String getUniqueEmail(){
        return "foo_UserServiceTest_" + counter.getAndIncrement() + "@test.com";
    }
}
