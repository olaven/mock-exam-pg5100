package kristiania.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Location.GET_ALL_LOCATIONS, query =  "select location from Location location")
})
@Entity
public class Location {

    public static final String GET_ALL_LOCATIONS = "GET_ALL_LOCATIONS";
    @GeneratedValue
    @Id
    private Long id;

    @NotBlank
    @Size(max = 55)
    private String name;

    @NotBlank
    @Size(max = 350)
    private String description;

    @NotNull
    @OneToMany(mappedBy = "location")
    private List<Trip> trips;

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
