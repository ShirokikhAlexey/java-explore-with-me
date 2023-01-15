package ru.prakticum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.prakticum.ewm.category.model.Category;
import ru.prakticum.ewm.compilation.model.Compilation;
import ru.prakticum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @NonNull
    @Column(name = "description_short", nullable = false)
    private String annotation;

    @Column(name = "description_full")
    private String description;

    @ManyToMany()
    @JoinTable(
            name = "event_category",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categories;

    @NonNull
    @Column(name = "dtc", columnDefinition = "TIMESTAMP", insertable = false)
    private LocalDateTime createdOn;

    @NonNull
    @Column(name = "start", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User initiator;

    @OneToMany(mappedBy = "event")
    private List<Location> locations;

    @NonNull
    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @NonNull
    @Column(name = "users_limit", nullable = false)
    private Integer participantLimit;

    @NonNull
    @Column(name = "published", columnDefinition = "TIMESTAMP", insertable = false)
    private LocalDateTime publishedOn;

    @NonNull
    @Column(name = "moderation_required", nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status state;

    @NonNull
    @Column(name = "name", nullable = false)
    private String title;

    @NonNull
    @Column(name = "views", nullable = false)
    private Integer views;

    @ManyToMany()
    @JoinTable(
            name = "user_event",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;

    @ManyToMany()
    @JoinTable(
            name = "event_collection",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "collection_id")}
    )
    private List<Compilation> compilations;

    @OneToMany(mappedBy = "event")
    private List<Request> requests;

    public Event() {}

    public Event(Integer id, String annotation, String description,
                 List<Category> categories, LocalDateTime eventDate, User initiator,
                 Boolean paid, String title, Integer views) {
        this.id = id;
        this.annotation = annotation;
        this.description = description;
        this.categories = categories;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.paid = paid;
        this.title = title;
        this.views = views;
    }
}
