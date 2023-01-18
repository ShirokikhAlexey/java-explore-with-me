package ru.prakticum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import ru.prakticum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_event")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @NonNull
    @Column(name = "dtc", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdOn;

    @NonNull
    @Column(name = "dtu", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedOn;

    public Request(User user, Event event, RequestStatus status) {
        this.status = status;
        this.event = event;
        this.user = user;
    }

    public Request(){}
}
