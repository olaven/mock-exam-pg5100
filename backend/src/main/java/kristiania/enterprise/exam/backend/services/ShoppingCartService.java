package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.ShoppingCart;
import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ShoppingCartService {

    @PersistenceContext
    private EntityManager entityManager;

    @Async
    public void addToShoppingCart(UserEntity user, Trip trip) {

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, user);
        if (shoppingCart == null) {

            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCart.setTrips(new ArrayList<>());
        }

        shoppingCart.getTrips().add(trip);
        entityManager.merge(shoppingCart);
    }

    //TODO: implement booking all items
    //TODO: implement removing items
    //TODO: Write tests
    //TODO: implement frontend (separate from direct booking)

    public int getTotal(UserEntity user) {

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, user);
        return shoppingCart.getTrips().stream()
                .mapToInt(Trip::getCost)
                .sum();
    }

    private boolean createShoppingCard(UserEntity user) {

        boolean present = entityManager.find(ShoppingCart.class, user) != null;
        if(present) {
            return false;
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTrips(new ArrayList<>());

        entityManager.persist(shoppingCart);
        return true;
    }
}
