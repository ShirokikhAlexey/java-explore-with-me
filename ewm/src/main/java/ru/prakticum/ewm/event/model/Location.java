package ru.prakticum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @NonNull
    @Column(name = "dtc", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dtc;

    @NonNull
    @Column(name = "dtu", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dtu;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Location() {
    }
}
