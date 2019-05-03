package kristiania.enterprise.exam.backend.repository;

import kristiania.enterprise.exam.backend.entity.ShoppingCart;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    public ShoppingCart findByUser(UserEntity user);
}
