package ru.prakticum.ewm.compilation.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.prakticum.ewm.event.model.Event;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "collections")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    @NonNull
    @Column(name = "show_on_main", nullable = false)
    private Boolean show_on_main;

    @NonNull
    @Column(name = "dtc", columnDefinition = "TIMESTAMP", insertable = false)
    private LocalDateTime dtc;

    @NonNull
    @Column(name = "dtu", columnDefinition = "TIMESTAMP", insertable = false)
    private LocalDateTime dtu;

    @ManyToMany(mappedBy = "compilations")
    private List<Event> events;

    public Compilation() {}

    public Compilation(Integer id, String title, Boolean pinned,List<Event> events) {
        this.id = id;
        this.name = title;
        this.show_on_main = pinned;
        this.events = events;
    }
}
