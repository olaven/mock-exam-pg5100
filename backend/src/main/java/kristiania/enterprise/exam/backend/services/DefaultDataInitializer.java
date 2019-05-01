package kristiania.enterprise.exam.backend.services;

/**
 * The approach in this file is heavily inspired by:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/734b4a660d8e1d815e2600c0e84cea5920bc5572/intro/exercise-solutions/quiz-game/part-10/src/main/java/org/tsdes/intro/exercises/quizgame/service/DefaultDataInitializerService.java
 */

import kristiania.enterprise.exam.backend.Season;
import kristiania.enterprise.exam.backend.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializer {

    @Autowired
    private UserService userService;
    @Autowired
    private TripService tripService;
    @Autowired
    private LocationService locationService;

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
        attempt(() -> userService.createUser("none@booked.com", "givennothing", "familynothing", "dev"));
        attempt(() -> userService.createUser("iron@man.com", "Tony", "Stark", "suit"));
        attempt(() -> userService.createUser("spider@man.com", "Peter", "Parker", "nyc"));
        attempt(() -> userService.createUser("black@widow.com", "Scarlet", "Johanson", "secret"));
        attempt(() -> userService.createUser("thor@valhal.com", "Thor", "Odins Son", "rainbowroad"));
        attempt(() -> userService.createUser("hulk@green.com", "Robert", "Banner", "sumo-goblin"));
        attempt(() -> userService.createUser("captain@america.com", "Steven", "Rogers", "Betsy Ross"));
        attempt(() -> userService.createUser("hawk@eye.com", "Clinton", "Barton", "arrow-to-the-knee"));



        // LOCATIONS
        Long alaskaId = attempt(() -> locationService.createLocation("Alaska", "Far north in North America"));
        Long netherlandsId = attempt(() -> locationService.createLocation("The Netherlands", "A flat land with nice buildings, flowers and comfortable climate"));
        Long norwayId = attempt(() -> locationService.createLocation("Norway", "A land of mountains and oil"));
        Long londonId = attempt(() -> locationService.createLocation("London", "Visit the huge city, important for finance and other stuff"));

        Location alaska = locationService.getLocation(alaskaId);
        Location netherlands = locationService.getLocation(netherlandsId);
        Location norway = locationService.getLocation(norwayId);
        Location london = locationService.getLocation(londonId);

        // TRIPS
        Long christmasInAlaska = attempt(() -> tripService.createTrip("Christmas in Alaska", "This is a trip for the whole family", 2999, alaska, Season.CHRISTMAS, LocalDate.now().plusMonths(2)));
        Long easterInTheNetherlands = attempt(() -> tripService.createTrip("Easter in The Netherlands", "Unforgettable easter among tulips!", 1999, netherlands, Season.EASTER, LocalDate.now().plusMonths(5)));
        Long netherlandsAutumn = attempt(() -> tripService.createTrip("Netherlands Autumn", "Watch windmills among the autumn-leaves", 1449, netherlands, Season.AUTUMN, LocalDate.now().plusMonths(1)));
        Long summerInAlaska = attempt(() -> tripService.createTrip("Summer in Alaska", "Great mountain hikes and trips to lakes", 3599, alaska, Season.SUMMER, LocalDate.now().plusMonths(7)));
        Long norwegianSpring = attempt(() -> tripService.createTrip("Norwegian in spring", "Enjoy the soothing mood in Norway's spring", 999999, norway, Season.SPRING, LocalDate.now().plusMonths(3)));
        Long skiingInNorway = attempt(() -> tripService.createTrip("Skiing in Norway", "Go skiing in the mountains of norway. Options for all kinds of winter activities", 93999, norway, Season.WINTER, LocalDate.now().plusMonths(3)));
        Long visitOslo = attempt(() -> tripService.createTrip("New yeas in Oslo", "Celebreate the new yar in oslo", 34343, norway, Season.NEW_YEARS, LocalDate.now().plusMonths(2)));
        Long easterInLondon = attempt(() -> tripService.createTrip("Easter in London", "London offers huge opportunities for easter", 1999, london, Season.EASTER, LocalDate.now().plusMonths(2)));
        Long christmasInLondon = attempt(() -> tripService.createTrip("Christmas in London", "London offers huge opportunities for Christmas", 3333, london, Season.CHRISTMAS, LocalDate.now().plusMonths(12)));
        Long halloweenInLondon = attempt(() -> tripService.createTrip("Halloween in London", "London offers huge opportunities for Halloween", 2888, london, Season.HALLOWEEN, LocalDate.now().plusMonths(6)));


        // BOOKINGS
        attempt(() -> userService.bookTrip("black@widow.com", christmasInAlaska));

        attempt(() -> userService.bookTrip("dev@mail.com", easterInTheNetherlands));
        attempt(() -> userService.bookTrip("iron@man.com", easterInTheNetherlands));
        attempt(() -> userService.bookTrip("spider@man.com", easterInTheNetherlands));
        attempt(() -> userService.bookTrip("black@widow.com", easterInTheNetherlands));
        attempt(() -> userService.bookTrip("dev@mail.com", easterInTheNetherlands));

        attempt(() -> userService.bookTrip("dev@mail.com", netherlandsAutumn));
        attempt(() -> userService.bookTrip("iron@man.com", netherlandsAutumn));
        attempt(() -> userService.bookTrip("spider@man.com", netherlandsAutumn));
        attempt(() -> userService.bookTrip("black@widow.com", netherlandsAutumn));
        attempt(() -> userService.bookTrip("thor@valhal.com", netherlandsAutumn));

        attempt(() -> userService.bookTrip("dev@mail.com", summerInAlaska));
        attempt(() -> userService.bookTrip("spider@man.com", summerInAlaska));
        attempt(() -> userService.bookTrip("dev@mail.com", summerInAlaska));

        attempt(() -> userService.bookTrip("spider@man.com", norwegianSpring));
        attempt(() -> userService.bookTrip("dev@mail.com", norwegianSpring));
        attempt(() -> userService.bookTrip("thor@valhal.com", norwegianSpring));

        attempt(() -> userService.bookTrip("spider@man.com", skiingInNorway));

        attempt(() -> userService.bookTrip("spider@man.com", visitOslo));
        attempt(() -> userService.bookTrip("hulk@green.com", visitOslo));
        attempt(() -> userService.bookTrip("black@widow.com", visitOslo));

        attempt(() -> userService.bookTrip("spider@man.com", easterInLondon));
        attempt(() -> userService.bookTrip("dev@mail.com", easterInLondon));
        
        attempt(() -> userService.bookTrip("black@widow.com", christmasInLondon));
        attempt(() -> userService.bookTrip("spider@man.com", christmasInLondon));

        attempt(() -> userService.bookTrip("iron@man.com", halloweenInLondon));

    }

    private<T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}