package kristiania.enterprise.exam.backend.repository;

import kristiania.enterprise.exam.backend.entity.TripEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends CrudRepository<TripEntity, Long>{

}
