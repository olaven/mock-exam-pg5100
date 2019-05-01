package kristiania.enterprise.exam.backend.repository;

import kristiania.enterprise.exam.backend.entity.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long>{

}
