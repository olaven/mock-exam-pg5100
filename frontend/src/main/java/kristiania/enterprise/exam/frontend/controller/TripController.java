package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.services.BookingService;
import kristiania.enterprise.exam.backend.services.TripService;
import kristiania.enterprise.exam.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScope
public class TripController {

    @Autowired
    TripService tripService;
    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;

    @Autowired
    UserInfoController userInfoController;

    public List<Trip> getTopTrips(int n) {

        List<Trip> topTrips = tripService.getTopTrips(n);
        return topTrips;
    }

    public List<Trip> getGetAllTrips() {

        List<Trip> trips = tripService.getAllTrips();
        return trips;
    }

    public String goToTripPage(Long id) {

        // figure out how to pass argument
        return "trip.jsf?id=" + id + "&faces-redirect=true";
    }


    public Trip getTrip(String idAsString) {

        Long id = Long.valueOf(idAsString);
        Trip trip = tripService.getTrip(id);
        return trip;
    }

    public void bookTrip(String idAsString) {

        Long tripId = Long.valueOf(idAsString);
        String userEmail = userInfoController.getUserEmail();

        userService.bookTrip(userEmail, tripId);
    }
}
