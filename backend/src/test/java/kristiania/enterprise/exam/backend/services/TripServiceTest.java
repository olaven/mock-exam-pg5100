package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Trip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest extends ServiceTestBase {


    @Autowired
    @Test
    public void canInsertTrip() {

        Long id = persistTrip();
        Trip trip = tripService.getTrip(id);

        assertNotNull(trip);
    }

    @Test
    public void canGetTripByLocationName() {

        String locationName = "other test location name";
        int n = 4;

        int sizeBefore = tripService.getTripsByLocationName(locationName).size();

        for (int i = 0; i < n; i++) {
            persistTrip(locationName);
        }

        List<Trip> retrieved = tripService.getTripsByLocationName(locationName);

        assertEquals(retrieved.size(), sizeBefore + n);
    }

    @Test
    public void canGetTripByCostLessThan5() {

        int validCost = 4;
        int invalidCost = 5;

        Random random = new Random();
        int validCount = random.nextInt(10);
        int invalidCount = random.nextInt(10);

        for (int i = 0; i < validCount; i++) {
            persistTrip(validCost);
        }

        for (int i = 0; i < invalidCount; i++) {
            persistTrip(invalidCost);
        }

        List<Trip> validTrips = tripService.getTripsByCostLessThan(5);
        assertEquals(validCount, validTrips.size());
    }


    @Test
    public void getTripsWithCostGreaterThan5() {

        int validCost = 6;
        int invalidCost = 5;

        Random random = new Random();
        int validCount = random.nextInt(10);
        int invalidCount = random.nextInt(10);

        for (int i = 0; i < validCount; i++) {
            persistTrip(validCost);
        }

        for (int i = 0; i < invalidCount; i++) {
            persistTrip(invalidCost);
        }

        List<Trip> validTrips = tripService.getTripsByCostGreaterThan(5);
        assertEquals(validCount, validTrips.size());
    }

    @Test
    public void canGetTopTrips() {

        String email = persistUser();
        Long first = persistTrip();
        Long second = persistTrip();
        Long unpopular = persistTrip();

        for (int i = 0; i < 5; i++) {
            userService.bookTrip(email, first);
        }

        for (int i = 0; i < 3; i++) {
            userService.bookTrip(email, second);
        }

        //NOTE: "unpopular" never booked

        List<Trip> topTrips = tripService.getTopTrips(2);
        boolean unpopularFound = topTrips.stream()
                .filter(trip -> trip.getId().equals(unpopular))
                .findFirst()
                .isPresent();

        boolean firstIsFirst = topTrips.stream()
                .map(trip -> trip.getId())
                .findFirst()
                .get()
                .equals(first);

        boolean secondIsSecond = topTrips.stream()
                .map(trip -> trip.getId())
                .toArray()[1]
                .equals(second);

        assertEquals(2, topTrips.size());
        assertFalse(unpopularFound);
        assertTrue(firstIsFirst);
        assertTrue(secondIsSecond);
    }


    @Test
    public void canGetTopTripsWithMoreData() {

        String email = persistUser();

        Long first = persistTrip();
        Long second = persistTrip();
        Long moderate1 = persistTrip();
        Long moderate2 = persistTrip();
        Long unpopular = persistTrip();

        for (int i = 0; i < 5; i++) {
            userService.bookTrip(email, first);
        }

        for (int i = 0; i < 3; i++) {
            userService.bookTrip(email, second);
        }

        userService.bookTrip(email, moderate1);
        userService.bookTrip(email, moderate2);

        //NOTE: "unpopular" never booked

        List<Trip> topTrips = tripService.getTopTrips(3);
        boolean unpopularFound = topTrips.stream()
                .filter(trip -> trip.getId().equals(unpopular))
                .findFirst()
                .isPresent();

        boolean firstIsFirst = topTrips.stream()
                .map(trip -> trip.getId())
                .findFirst()
                .get()
                .equals(first);

        boolean secondIsSecond = topTrips.stream()
                .map(trip -> trip.getId())
                .toArray()[1]
                .equals(second);

        assertEquals(3, topTrips.size());
        assertFalse(unpopularFound);
        assertTrue(firstIsFirst);
        assertTrue(secondIsSecond);
    }

    @Test
    public void canDeleteTrip() {

        Long id = persistTrip();
        assertNotNull(tripService.getTrip(id));

        tripService.deleteTrip(id);
        assertNull(tripService.getTrip(id));
    }

    @Test
    public void testCannotDeleteTripIfNotExists() {

        // NOTE: no trip persisted
        boolean deleted = tripService.deleteTrip(-1l);
        assertFalse(deleted);
    }

    @Test
    public void canGetByTitleNameAndQuery() {

        Long locationId = persistLocation();
        String locationName = locationService.getLocation(locationId).getName();
        String query = "a";

        // Find these four
        persistTrip("a", locationName);
        persistTrip("ba", locationName);
        persistTrip("aa", locationName);
        persistTrip("bA", locationName);

        // Do NOT find these four
        persistTrip("n", locationName);
        persistTrip("o", locationName);
        persistTrip("n", locationName);
        persistTrip("e", locationName);

        List<Trip> results = tripService.getSearchResults(locationName, query);
        results.forEach(result -> {
            assertTrue(result.getTitle().toLowerCase().contains(query));
        });
    }

    @Test
    public void testSearchResultsAreOrderedByCost() {

        Long locationId = persistLocation();
        String locationName = locationService.getLocation(locationId).getName();

        Long first = persistTrip(1, locationName);
        Long second = persistTrip(2, locationName);
        Long third = persistTrip(3, locationName);
        Long fourth = persistTrip(4, locationName);

        // NOTE: all test trips should have titles with "title"
        List<Trip> results = tripService.getSearchResults(locationName, "title");

        assertEquals(4, results.size());
        assertEquals(first, results.get(0).getId());
        assertEquals(second, results.get(1).getId());
        assertEquals(third, results.get(2).getId());
        assertEquals(fourth, results.get(3).getId());
    }


    @Test
    public void canGetAllTrips() {

        assertEquals(0, tripService.getAllTrips().size());
        int n = 10;

        for (int i = 0; i < n; i++) {
            persistTrip();
        }

        int count = tripService.getAllTrips().size();
        assertEquals(n, count);
    }


}