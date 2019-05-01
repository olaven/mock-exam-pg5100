package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.TripEntity;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest extends ServiceTestBase {


    @Autowired
    @Test
    public void canInsertTrip() {

        Long id = persistDefaultTrip();
        TripEntity trip = tripService.getTrip(id);

        assertNotNull(trip);
    }

    //TODO: tests for all other methods in tripservice
    @Test
    public void canGetTripByLocationName() {

        String locationName = "other test location name";
        int n = 4;

        int sizeBefore = tripService.getTripsByLocationName(locationName).size();

        for (int i = 0; i < n; i++) {
            persistTripWithLocationName(locationName);
        }

        List<TripEntity> retrieved = tripService.getTripsByLocationName(locationName);

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
            persistTripWithCost(validCost);
        }

        for (int i = 0; i < invalidCount; i++) {
            persistTripWithCost(invalidCost);
        }

        List<TripEntity> validTrips = tripService.getTripsByCostLessThan(5);
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
            persistTripWithCost(validCost);
        }

        for (int i = 0; i < invalidCount; i++) {
            persistTripWithCost(invalidCost);
        }

        List<TripEntity> validTrips = tripService.getTripsByCostGreaterThan(5);
        assertEquals(validCount, validTrips.size());
    }

    @Test
    public void canGetTopTrips() {

        String email = persistUser();
        Long first = persistDefaultTrip();
        Long second = persistDefaultTrip();
        Long unpopular = persistDefaultTrip();

        for (int i = 0; i < 5; i++) {
            userService.bookTrip(email, first);
        }

        for (int i = 0; i < 3; i++) {
            userService.bookTrip(email, second);
        }

        //NOTE: "unpopular" never booked

        List<TripEntity> topTrips = tripService.getTopTrips(2);
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



}