package ru.prakticum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import ru.prakticum.ewm.category.model.Category;
import ru.prakticum.ewm.compilation.model.Compilation;
import ru.prakticum.ewm.location.model.Location;
import ru.prakticum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
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
    @ColumnDefault("current_timestamp")
    private LocalDateTime createdOn;

    @NonNull
    @Column(name = "start", columnDefinition = "TIMESTAMP")
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User initiator;

    @ManyToMany()
    @JoinTable(
            name = "event_location",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "location_id")}
    )
    private List<Location> locations;

    @NonNull
    @Column(name = "paid")
    @ColumnDefault("true")
    private Boolean paid;

    @NonNull
    @Column(name = "users_limit")
    @ColumnDefault("0")
    private Integer participantLimit;

    @NonNull
    @Column(name = "published", columnDefinition = "TIMESTAMP")
    @ColumnDefault("current_timestamp")
    private LocalDateTime publishedOn;

    @NonNull
    @Column(name = "moderation_required")
    @ColumnDefault("true")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status state;

    @NonNull
    @Column(name = "name")
    private String title;

    @NonNull
    @Column(name = "views")
    @ColumnDefault("0")
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

    public Event(Integer id, String annotation, String description,
                 List<Category> categories, LocalDateTime eventDate, User initiator,
                 Boolean paid, String title, Integer views, LocalDateTime createdOn, Status status, Integer participantLimit,
                 Location location, Boolean requestModeration) {
        this.id = id;
        this.annotation = annotation;
        this.description = description;
        this.categories = categories;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.paid = paid;
        this.title = title;
        this.views = views;
        this.createdOn = createdOn;
        this.state = status;
        this.participantLimit = participantLimit;
        this.locations = List.of(location);
        this.requestModeration = requestModeration;
    }
}
