package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Season;
import kristiania.enterprise.exam.backend.entity.Location;
import kristiania.enterprise.exam.backend.entity.Trip;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long createTrip(String title, String description, int cost, Location location, Season season, LocalDate date) {

        Trip trip = new Trip();

        trip.setTitle(title);
        trip.setDescription(description);
        trip.setCost(cost);
        trip.setLocation(location);
        trip.setSeason(season);
        trip.setDate(date);

        entityManager.persist(trip);

        return trip.getId();
    }


    @Transactional
    public boolean deleteTrip(Long id) {

        Trip trip = entityManager.find(Trip.class, id);
        if (trip == null) {
            return false;
        }

        entityManager.remove(trip);
        return true;
    }

    public Trip getTrip(Long id) {

        return entityManager.find(Trip.class, id);
    }

    public List<Trip> getTopTrips(int n) {

        Query query = entityManager.createNamedQuery(Trip.GET_TOP_TRIPS, Trip.class);
        List<Trip> results = query.setMaxResults(n).getResultList();

        return results;
    }

    public List<Trip> getTripsByLocationName(String locationName) {

        Query query = entityManager.createNamedQuery(Trip.GET_TRIP_BY_LOCATION_NAME, Trip.class);
        query.setParameter("locationName", locationName);

        return query.getResultList();
    }

    public List<Trip> getSearchResults(String locationName, String titleQuery) {

        List<Trip> byLocation = getTripsByLocationName(locationName);
        List<Trip> results = byLocation.stream()
                .filter(trip -> trip.getTitle().toLowerCase().contains(titleQuery.toLowerCase()))
                .collect(Collectors.toList());
        results.sort(Comparator.comparing(Trip::getCost));

        return results;
    }

    public List<Trip> getTripsByCostLessThan(int max) {

        Query query = entityManager.createNamedQuery(Trip.GET_TRIP_BY_COST_LESS_THAN, Trip.class);
        query.setParameter("max", max);

        return query.getResultList();
    }

    public List<Trip> getTripsByCostGreaterThan(int min) {

        Query query = entityManager.createNamedQuery(Trip.GET_TRIP_BY_COST_GREATER_THAN, Trip.class);
        query.setParameter("min", min);

        return query.getResultList();
    }

    public List<Trip> getAllTrips() {

        return entityManager.createQuery("select trip from Trip  trip ").getResultList();
    }
}
