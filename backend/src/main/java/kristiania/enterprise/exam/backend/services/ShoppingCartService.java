package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.ShoppingCart;
import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import kristiania.enterprise.exam.backend.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class ShoppingCartService {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartRepository repository;

    public Long createShoppingCart(UserEntity user) {

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTrips(new ArrayList<>());

        entityManager.persist(shoppingCart);
        user.setShoppingCart(shoppingCart);
        entityManager.merge(user);
        return shoppingCart.getId();
    }

    @Transactional
    public void addToShoppingCart(UserEntity user, Trip trip) {

        ShoppingCart shoppingCart = repository.findByUser(user);
        if (shoppingCart == null) {

            Long id = createShoppingCart(user);
            shoppingCart = entityManager.find(ShoppingCart.class, id);
        }

        shoppingCart.getTrips().add(trip);
        entityManager.merge(shoppingCart);
    }

    @Transactional
    public void bookAllItems(UserEntity user) {

        ShoppingCart shoppingCart = repository.findByUser(user);
        shoppingCart.getTrips().forEach(trip -> {

            userService.bookTrip(user.getEmail(), trip.getId());
        });

        shoppingCart.setTrips(new ArrayList<>());
        entityManager.merge(shoppingCart);
    }

    @Transactional
    public void emptyShoppingCart(UserEntity user) {

        ShoppingCart shoppingCart = repository.findByUser(user);
        shoppingCart.setTrips(new ArrayList<>());
        entityManager.merge(shoppingCart);
    }

    //TODO: implement frontend (separate from direct booking)

    public int getTotal(UserEntity user) {

        ShoppingCart shoppingCart = repository.findByUser(user);
        return shoppingCart.getTrips().stream()
                .mapToInt(Trip::getCost)
                .sum();
    }

    public int getTripCount(UserEntity user) {

        return repository
                .findByUser(user)
                .getTrips()
                .size();
    }
}
