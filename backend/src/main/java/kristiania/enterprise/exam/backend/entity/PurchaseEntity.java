package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class PurchaseEntity {

    @Id
    private Long id;

    @ManyToOne
    @NotNull
    private UserEntity user;

    @ManyToOne
    @NotNull
    TripEntity trip;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date timeOfPurchase;

    public PurchaseEntity() {
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

    public Date getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public void setTimeOfPurchase(Date timeOfPurchase) {
        this.timeOfPurchase = timeOfPurchase;
    }
}
