package ru.prakticum.ewm.event.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "lat", nullable = false)
    private Float lat;

    @NonNull
    @Column(name = "lon", nullable = false)
    private Float lon;

    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "dtc", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dtc;

    @NonNull
    @Column(name = "dtu", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dtu;

    @ManyToMany()
    @JoinTable(
            name = "event_location",
            joinColumns = {@JoinColumn(name = "location_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private List<Event> events;

    public Location(Float lat, Float lon, String description) {
        this.lat = lat;
        this.lon = lon;
        this.description = description;
    }
}
