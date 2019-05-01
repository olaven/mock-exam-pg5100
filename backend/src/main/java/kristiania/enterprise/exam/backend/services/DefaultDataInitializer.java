package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Season;
import kristiania.enterprise.exam.backend.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializer {

    @Autowired
    private UserService userService;
    @Autowired
    private TripService tripService;

    @PostConstruct
    public void initialize() {

        insertDefaultUsers();
        insertDefaultTrips();
    }

    private void insertDefaultUsers() {

    }

    private void insertDefaultTrips() {

        // USERS
        attempt(() -> userService.createUser("dev@mail.com", "dev-given", "dev-family", "dev"));
        attempt(() -> userService.createUser("iron@man.com", "Tony", "Stark", "suit"));
        attempt(() -> userService.createUser("spider@man.com", "Peter", "Parker", "nyc"));


        // LOCATIONS (created through cascade)
        Location alaska = new Location();
        alaska.setName("Alaska");
        alaska.setDescription("Up northernest North America");
        alaska.setTrips(new ArrayList<>());

        Location netherlands = new Location();
        netherlands.setName("The Netherlands");
        netherlands.setDescription("A flat land with nice buildings, flowers and comfortable climate");
        netherlands.setTrips(new ArrayList<>());

        // TRIPS
        Long christmasInAlaska = attempt(() -> tripService.createTrip("Christmas in Alaska", "This is a trip for the whole family", 2999, alaska, Season.CHRISTMAS, LocalDate.now().plusMonths(2)));
        Long easterInTheNetherlands = attempt(() -> tripService.createTrip("Easter in The Netherlands", "Unforgettable easter among tulips!", 1999, netherlands, Season.EASTER, LocalDate.now().plusMonths(5)));
        Long netherlandsAutumn = attempt(() -> tripService.createTrip("Netherlands Autumn", "Watch windmills among the autumn-leaves", 1449, netherlands, Season.AUTUMN, LocalDate.now().plusMonths(1)));
        Long summerInAlaska = attempt(() -> tripService.createTrip("Summer in Alaska", "Great mountain hikes and trips to lakes", 3599, alaska, Season.SUMMER, LocalDate.now().plusMonths(7)));

        // BOOKINGS
        attempt(() -> userService.bookTrip("dev@mail.com", christmasInAlaska));
        attempt(() -> userService.bookTrip("dev@mail.com", christmasInAlaska));
        attempt(() -> userService.bookTrip("dev@mail.com", christmasInAlaska));


        attempt(() -> userService.bookTrip("dev@mail.com", easterInTheNetherlands));
        attempt(() -> userService.bookTrip("iron@man.com", easterInTheNetherlands));
        attempt(() -> userService.bookTrip("spider@man.com", easterInTheNetherlands));
        attempt(() -> userService.bookTrip("dev@mail.com", easterInTheNetherlands));
        attempt(() -> userService.bookTrip("dev@mail.com", easterInTheNetherlands));

        attempt(() -> userService.bookTrip("dev@mail.com", netherlandsAutumn));
        attempt(() -> userService.bookTrip("iron@man.com", netherlandsAutumn));
        attempt(() -> userService.bookTrip("spider@man.com", netherlandsAutumn));
        attempt(() -> userService.bookTrip("spider@man.com", netherlandsAutumn));
        attempt(() -> userService.bookTrip("dev@mail.com", netherlandsAutumn));

        attempt(() -> userService.bookTrip("dev@mail.com", summerInAlaska));
        attempt(() -> userService.bookTrip("spider@man.com", summerInAlaska));
        attempt(() -> userService.bookTrip("dev@mail.com", summerInAlaska));
    }

    private<T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}