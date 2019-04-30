package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Season;
import kristiania.enterprise.exam.backend.entity.TripEntity;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Service
public class TripService {

    @PersistenceContext
    private EntityManager entityManager;

    public Long createTrip(String title, String description, String location, Season season, Date date) {

        TripEntity trip = new TripEntity();

        trip.setTitle(title);
        trip.setDescription(description);
        trip.setLocation(location);
        trip.setSeason(season);
        trip.setDate(date);

        entityManager.persist(trip);

        return trip.getId();
    }
}
