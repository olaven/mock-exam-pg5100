package kristiania.enterprise.exam.frontend.controller;

import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import kristiania.enterprise.exam.backend.services.ShoppingCartService;
import kristiania.enterprise.exam.backend.services.TripService;
import kristiania.enterprise.exam.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScope
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private TripService tripService;
    @Autowired
    private UserService userService;
    @Autowired
    UserInfoController userInfoController;

    public String addToShoppingCart(Long tripId) {

        UserEntity user = userInfoController.getUser();
        Trip trip = tripService.getTrip(tripId);

        shoppingCartService.addToShoppingCart(user, trip);
        return "/shoppingCart.jsf?faces-redirect=true";
    }

    public List<Trip> getAllTrips() {

        UserEntity user = userInfoController.getUser();
        return shoppingCartService.getAllTrips(user);
    }

    public boolean contains(Long tripId) {

        Trip trip = tripService.getTrip(tripId);
        UserEntity user = userInfoController.getUser();
        return shoppingCartService.getAllTrips(user).contains(trip);
    }

    public int getTotal() {

        UserEntity user = userInfoController.getUser();
        return shoppingCartService.getTotal(user);
    }

    public String bookTrips() {

        UserEntity user = userInfoController.getUser();
        shoppingCartService.bookAllItems(user);
        return "/shoppingCart.jsf?faces-redirect=true";
    }
}
