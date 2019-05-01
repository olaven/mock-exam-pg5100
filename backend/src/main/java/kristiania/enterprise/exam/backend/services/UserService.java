package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.BookingEntity;
import kristiania.enterprise.exam.backend.entity.TripEntity;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
    private PasswordEncoder passwordEncoder;

    @Autowired
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
        TripEntity trip = find(TripEntity.class, tripId);

        BookingEntity booking = new BookingEntity();
        booking.setUser(user);
        booking.setTrip(trip);
        booking.setTimeOfPurchase(LocalDate.now());

        entityManager.persist(booking);
        return booking.getId();
    }

    public List<BookingEntity> getBookings(String userEmail) {

        Query query = entityManager.createNamedQuery(BookingEntity.GET_BOOKINGS_BY_USER, BookingEntity.class);
        query.setParameter("email", userEmail);

        List<BookingEntity> bookings = query.getResultList();
        return bookings;
    }



    private <T, K> T find(Class<T> c, K id) {

        T entry = entityManager.find(c, id);
        if (entry == null) {
            throw new IllegalArgumentException("entry is not in database");
        }

        return entry;
    }
}
