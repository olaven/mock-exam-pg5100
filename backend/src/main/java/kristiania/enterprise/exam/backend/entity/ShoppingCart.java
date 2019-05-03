package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue
    Long id;

    //NOTE: @Id is not allowed on OneToOne/similar
    @OneToOne(mappedBy = "shoppingCart")
    private UserEntity user;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
    private List<Trip> trips;

    public ShoppingCart() {
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

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
