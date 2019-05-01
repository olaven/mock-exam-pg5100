package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = BookingEntity.GET_BOOKINGS_BY_USER, query = "select booking from BookingEntity booking where user.email = :email")
})
@Entity
public class BookingEntity {

    public static final String GET_BOOKINGS_BY_USER = "GET_BOOKINGS_BY_USER";

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private UserEntity user;

    @ManyToOne
    @NotNull
    TripEntity trip;

    @NotNull
    private LocalDate timeOfPurchase;

    public BookingEntity() {
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

    public TripEntity getTrip() {
        return trip;
    }

    public void setTrip(TripEntity trip) {
        this.trip = trip;
    }

    public void setTimeOfPurchase(LocalDate timeOfPurchase) {
        this.timeOfPurchase = timeOfPurchase;
    }
}
