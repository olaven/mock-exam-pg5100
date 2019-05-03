package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.ShoppingCart;
import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import sun.security.provider.SHA;

import javax.net.ssl.SNIHostName;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ShoppingCartService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

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

    @Async
    public void bookAllItems(UserEntity user) {

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, user);
        shoppingCart.getTrips().forEach(trip -> {

            userService.bookTrip(user.getEmail(), trip.getId());
        });

        shoppingCart.setTrips(new ArrayList<>());
    }

    @Async
    public void emptyShoppingCart(UserEntity user) {

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, user);
        shoppingCart.setTrips(new ArrayList<>());
    }
    
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
