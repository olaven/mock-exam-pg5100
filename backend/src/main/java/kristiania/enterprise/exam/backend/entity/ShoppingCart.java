package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Embeddable
public class ShoppingCart {

    @Id
    @OneToOne(mappedBy = "shoppingCart")
    private UserEntity user;

    @NotNull
    @OneToMany
    private List<Trip> trips;

    public ShoppingCart() {
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
