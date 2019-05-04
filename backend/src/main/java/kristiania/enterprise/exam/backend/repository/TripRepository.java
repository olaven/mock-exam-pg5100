package kristiania.enterprise.exam.backend.repository;

import kristiania.enterprise.exam.backend.entity.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long>{

//    List<Trip> findAllByTitleContainingAndLocationNameEquals(String titleQuery, String locationName);
//    List<Trip> findAllByTitleContainingAndGetTripByLocationNameEquals(String titleQuery, String locationName);
}
