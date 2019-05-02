package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.Location;
import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.services.LocationService;
import kristiania.enterprise.exam.backend.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.inject.Named;
import java.util.List;

@SessionScope
@Named
public class SearchController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private TripService tripService;

    private String titleQuery;
    private String locationName;
    private List<Trip> results;

    public List<Location> getAllLocations() {

        List<Location> locations = locationService.getAllLocations();
        return locations;
    }

    public String doSearch() {

        if (titleQuery.trim().isEmpty() || locationName.trim().isEmpty()) {
            return "/search.jsf?faces-redirect=true&error=true";
        }

        setResults(tripService.getTripsByLocatioNameAndTitleQuery(locationName, titleQuery));
        return "/search.jsf?faces-redirect=true";
    }


    public String getTitleQuery() {
        return titleQuery;
    }

    public void setTitleQuery(String titleQuery) {
        this.titleQuery = titleQuery;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<Trip> getResults() {
        return results;
    }

    public void setResults(List<Trip> results) {
        this.results = results;
    }
}
