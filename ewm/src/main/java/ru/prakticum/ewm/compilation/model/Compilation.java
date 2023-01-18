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
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "show_on_main", nullable = false)
    private Boolean showOnMain;

    @NonNull
    @Column(name = "dtc", columnDefinition = "TIMESTAMP", insertable = false)
    private LocalDateTime dtc;

    @NonNull
    @Column(name = "dtu", columnDefinition = "TIMESTAMP", insertable = false)
    private LocalDateTime dtu;

    @ManyToMany()
    @JoinTable(
            name = "event_collection",
            joinColumns = {@JoinColumn(name = "collection_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private List<Event> events;

    public Compilation() {
    }

    public Compilation(Integer id, String title, Boolean pinned, List<Event> events) {
        this.id = id;
        this.name = title;
        this.showOnMain = pinned;
        this.events = events;
    }
}
