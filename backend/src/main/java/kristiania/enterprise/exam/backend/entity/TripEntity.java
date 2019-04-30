package kristiania.enterprise.exam.backend.entity;

import kristiania.enterprise.exam.backend.Season;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class TripEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 55)
    private String title;

    @NotBlank
    @Size(max = 150)
    private String description;

    @NotBlank
    @Size(max = 150)
    private String location;

    @Enumerated(EnumType.STRING)
    private Season season;

    @Temporal(TemporalType.DATE)
    private Date date;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
