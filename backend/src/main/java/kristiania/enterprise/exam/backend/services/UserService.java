package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Booking;
import kristiania.enterprise.exam.backend.entity.ShoppingCart;
import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * NOTE: This file is coped from:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/18f764c3123f60339ab98167790aa223641e7559/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/db/UserService.java
 */

/**
 * Created by arcuri82 on 13-Dec-17.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public boolean createUser(String email, String givenName, String familyName, String password) {

        boolean present = entityManager.find(UserEntity.class, email) != null;
        if (present) {

            return false;
        }

        String hashed = passwordEncoder.encode(password);

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setGivenName(givenName);
        user.setFamilyName(familyName);
        user.setPassword(hashed);
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        entityManager.persist(user);

        return true;
    }

    public Long bookTrip(String userEmail, Long tripId) {

        UserEntity user = find(UserEntity.class, userEmail);
        Trip trip = find(Trip.class, tripId);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTrip(trip);
        booking.setTimeOfPurchase(LocalDate.now());

        entityManager.persist(booking);
        return booking.getId();
    }

    public List<Trip> getBookedTrips(String userEmail) {

        Query query = entityManager.createNamedQuery(Booking.GET_TRIPS_BOOKED_BY_USER, Trip.class);
        query.setParameter("email", userEmail);

        return query.getResultList();
    }

    public boolean userHasBooked(String userEmail, Long tripId) {

        List<Trip> trips = getBookedTrips(userEmail);
        boolean found = trips.stream()
                .map(trip -> trip.getId())
                .collect(Collectors.toList())
                .contains(tripId);

        return found;
    }



    private <T, K> T find(Class<T> c, K id) {

        T entry = entityManager.find(c, id);
        if (entry == null) {
            throw new IllegalArgumentException("entry is not in database");
        }

        return entry;
    }

    public UserEntity getUser(String email) {

        UserEntity user = entityManager.find(UserEntity.class, email);
        return user;
    }
}
