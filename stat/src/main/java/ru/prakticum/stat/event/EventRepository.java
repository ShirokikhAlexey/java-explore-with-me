package ru.prakticum.stat.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.prakticum.stat.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, EventRepositoryCustom {
    @Query(value = "select e " +
            "from Event as e " +
            "where e.timestamp >= ?1 " +
            "and e.timestamp <= ?2 ")
    List<Event> getStatistics(LocalDateTime start, LocalDateTime end);

    @Query(value = "select distinct e " +
            "from Event as e " +
            "where e.timestamp >= ?1 " +
            "and e.timestamp <= ?2 ")
    List<Event> getStatisticsUnique(LocalDateTime start, LocalDateTime end);
}
