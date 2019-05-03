package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShoppingCartServiceTest extends ServiceTestBase {

    @Test
    public void testShoppingCartCreatedForUserOnlyWhenAdding() {

        String userEmail = persistUser();
        UserEntity user = userService.getUser(userEmail);
        assertNull(user.getShoppingCart());

        Long tripId = persistTrip();
        Trip trip = tripService.getTrip(tripId);

        shoppingCartService.addToShoppingCart(user, trip);

        UserEntity after = userService.getUser(userEmail);
        assertNotNull(after.getShoppingCart());
        assertEquals(1, after.getShoppingCart().getTrips().size());
    }

    @Test
    public void testCanBookAllItems() {

        Trip first = tripService.getTrip(persistTrip());
        Trip second = tripService.getTrip(persistTrip());
        Trip third = tripService.getTrip(persistTrip());

        String userEmail = persistUser();
        UserEntity user = userService.getUser(userEmail);

        shoppingCartService.addToShoppingCart(user, first);
        shoppingCartService.addToShoppingCart(user, second);
        shoppingCartService.addToShoppingCart(user, third);

        assertEquals(0, userService.getBookedTrips(userEmail).size());
        shoppingCartService.bookAllItems(user);
        assertEquals(3, userService.getBookedTrips(userEmail).size());
    }

    @Test
    public void testCanGetTotal() {

        Trip first = tripService.getTrip(persistTrip(100));
        Trip second = tripService.getTrip(persistTrip(150));
        Trip third = tripService.getTrip(persistTrip(250));

        String userEmail = persistUser();
        UserEntity user = userService.getUser(userEmail);

        shoppingCartService.addToShoppingCart(user, first);
        shoppingCartService.addToShoppingCart(user, second);
        shoppingCartService.addToShoppingCart(user, third);

        //100 + 150 + 250 = 500
        assertEquals(500, shoppingCartService.getTotal(user));
    }

    @Test
    public void canEmptyShoppingCart() {

        Trip first = tripService.getTrip(persistTrip());
        Trip second = tripService.getTrip(persistTrip());
        Trip third = tripService.getTrip(persistTrip());

        String userEmail = persistUser();
        UserEntity user = userService.getUser(userEmail);

        shoppingCartService.addToShoppingCart(user, first);
        shoppingCartService.addToShoppingCart(user, second);
        shoppingCartService.addToShoppingCart(user, third);

        assertEquals(3, shoppingCartService.getTripCount(user));
        shoppingCartService.emptyShoppingCart(user);
        assertEquals(0, shoppingCartService.getTripCount(user));
    }

}