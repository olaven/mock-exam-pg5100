package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.Location;
import kristiania.enterprise.exam.backend.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.util.List;

@RequestScope
@Named
public class SearchController {

    @Autowired
    private LocationService locationService;

    private String currentQuery;

    public List<Location> getAllLocations() {

        List<Location> locations = locationService.getAllLocations();
        return locations;
    }
    public String getCurrentQuery() {
        return currentQuery;
    }

    public void setCurrentQuery(String currentQuery) {
        this.currentQuery = currentQuery;
    }

    public void locationChangeListener(ValueChangeEvent event) {

        Object value = event.getNewValue();
        System.out.println(value);
    }
}
