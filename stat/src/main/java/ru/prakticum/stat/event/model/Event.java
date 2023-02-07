package ru.prakticum.stat.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String app;

    @NonNull
    private String uri;

    @NonNull
    private String ip;

    @NonNull
    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamp;

}
