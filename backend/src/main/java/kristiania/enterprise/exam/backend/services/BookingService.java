package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Booking;
import kristiania.enterprise.exam.backend.entity.Trip;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Service
public class BookingService {

    @PersistenceContext
    private EntityManager entityManager;

    public void createBooking(UserEntity user, Trip trip) {

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTrip(trip);
        booking.setTimeOfPurchase(LocalDate.now());

        entityManager.persist(booking);

    }
}
