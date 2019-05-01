package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Season;
import kristiania.enterprise.exam.backend.entity.LocationEntity;
import kristiania.enterprise.exam.backend.entity.TripEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TripService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long createTrip(String title, String description, int cost, LocationEntity location, Season season, LocalDate date) {

        TripEntity trip = new TripEntity();

        trip.setTitle(title);
        trip.setDescription(description);
        trip.setCost(cost);
        trip.setLocation(location);
        trip.setSeason(season);
        trip.setDate(date);

        entityManager.persist(trip);

        return trip.getId();
    }


    public boolean deleteTrip(Long id) {

        TripEntity trip = entityManager.find(TripEntity.class, id);
        if (trip == null) {
            return false;
        }


        entityManager.remove(trip);
        return true;
    }

    public TripEntity getTrip(Long id) {

        return entityManager.find(TripEntity.class, id);
    }

    public List<TripEntity> getTopTrips(int n) {

        Query query = entityManager.createNamedQuery(TripEntity.GET_TOP_TRIPS, TripEntity.class);
        List<TripEntity> results = query.setMaxResults(n).getResultList();

        return results;
    }

    public List<TripEntity> getTripsByLocationName(String locationName) {

        Query query = entityManager.createNamedQuery(TripEntity.GET_TRIP_BY_LOCATION_NAME, TripEntity.class);
        query.setParameter("locationName", locationName);

        return query.getResultList();
    }

    public List<TripEntity> getTripsByCostLessThan(int max) {

        Query query = entityManager.createNamedQuery(TripEntity.GET_TRIP_BY_COST_LESS_THAN, TripEntity.class);
        query.setParameter("max", max);

        return query.getResultList();
    }

    public List<TripEntity> getTripsByCostGreaterThan(int min) {

        Query query = entityManager.createNamedQuery(TripEntity.GET_TRIP_BY_COST_GREATER_THAN, TripEntity.class);
        query.setParameter("min", min);

        return query.getResultList();
    }

    public List<TripEntity> getAllTrips() {

        return entityManager.createQuery("select trip from TripEntity  trip ").getResultList();
    }
}
