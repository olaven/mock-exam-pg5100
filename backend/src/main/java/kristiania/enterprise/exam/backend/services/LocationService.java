package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Location;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class LocationService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long createLocation(String name, String description) {

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setTrips(new ArrayList<>());

        entityManager.persist(location);
        return location.getId();
    }

    public Location getLocation(Long id) {

        return entityManager.find(Location.class, id);
    }
}
