package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Booking.GET_TRIPS_BOOKED_BY_USER, query = "select booking.trip from Booking booking where booking.user.email = :email")
})
@Entity
public class Booking {

    public static final String GET_TRIPS_BOOKED_BY_USER = "GET_TRIPS_BOOKED_BY_USER";

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private UserEntity user;

    @ManyToOne
    @NotNull
    Trip trip;

    @NotNull
    private LocalDate timeOfPurchase;

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public void setTimeOfPurchase(LocalDate timeOfPurchase) {
        this.timeOfPurchase = timeOfPurchase;
    }
}
