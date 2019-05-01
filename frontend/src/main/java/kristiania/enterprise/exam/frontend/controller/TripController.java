package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TripController {

    @Autowired
    TripService tripService;

    public List<Trip> getTopTrips(int n) {

        List<Trip> topTrips = tripService.getTopTrips(n);
        return topTrips;
    }

    public String goToDetailsPage() {

        // figure out how to pass argument
        return null;
    }
}
