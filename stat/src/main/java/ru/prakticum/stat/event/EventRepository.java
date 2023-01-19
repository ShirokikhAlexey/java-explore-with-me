package ru.prakticum.stat.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.prakticum.stat.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "select e " +
            "from Event as e " +
            "where e.timestamp >= ?1 " +
            "and e.timestamp <= ?2 ")
    List<Event> getStatistics(LocalDateTime start, LocalDateTime end);

    @Query(value = "select e " +
            "from Event as e " +
            "where e.timestamp >= ?1 " +
            "and e.timestamp <= ?2 " +
            "and e.uri in ?3 ")
    List<Event> getStatisticsWithUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "select distinct e " +
            "from Event as e " +
            "where e.timestamp >= ?1 " +
            "and e.timestamp <= ?2 ")
    List<Event> getStatisticsUnique(LocalDateTime start, LocalDateTime end);

    @Query(value = "select distinct e " +
            "from Event as e " +
            "where e.timestamp >= ?1 " +
            "and e.timestamp <= ?2 " +
            "and e.uri in ?3 ")
    List<Event> getStatisticsWithUrisUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
