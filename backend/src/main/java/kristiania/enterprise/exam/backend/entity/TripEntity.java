package kristiania.enterprise.exam.backend.entity;

import kristiania.enterprise.exam.backend.Season;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = TripEntity.GET_TRIP_BY_LOCATION_NAME, query = "select trip from TripEntity trip where trip.location.name = :locationName"),
        @NamedQuery(name = TripEntity.GET_TRIP_BY_COST_LESS_THAN, query = "select trip from TripEntity trip where trip.cost < :max"),
        @NamedQuery(name = TripEntity.GET_TRIP_BY_COST_GREATER_THAN, query = "select trip from TripEntity trip where trip.cost > :min"),
        @NamedQuery(name = TripEntity.GET_TOP_TRIPS, query = "select booking.trip from BookingEntity booking group by booking.trip order by count(booking.user) desc"),
})
@Entity
public class TripEntity {

    public static final String GET_TRIP_BY_LOCATION_NAME = "GET_TRIP_BY_LOCATION_NAME";
    public static final String GET_TRIP_BY_COST_LESS_THAN = "GET_TRIP_BY_COST_LESS_THAN";
    public static final String GET_TRIP_BY_COST_GREATER_THAN = "GET_TRIP_BY_COST_GREATER_THAN";
    public static final String GET_TOP_TRIPS = "GET_TOP_TRIPS";

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 55)
    private String title;

    @NotBlank
    @Size(max = 150)
    private String description;

    @NotNull
    @Min(0)
    private Integer cost;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST) //do not need to create new locations explicitly
    private LocationEntity location;

    @Enumerated(EnumType.STRING)
    private Season season;

    @Future
    private LocalDate date;

    public TripEntity() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
