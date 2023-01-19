package ru.prakticum.ewm.category.model;

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
@Table(name = "categories")
public class Category {
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
    @Column(name = "dtc", columnDefinition = "TIMESTAMP", insertable = false)
    private LocalDateTime dtc;

    @NonNull
    @Column(name = "dtu", columnDefinition = "TIMESTAMP", insertable = false)
    private LocalDateTime dtu;

    @ManyToMany(mappedBy = "categories")
    private List<Event> events;

    public Category() {}

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
