package ru.prakticum.ewm.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.prakticum.ewm.event.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, EventRepositoryCustom {
    @Query(value = "SELECT e " +
            "FROM Event AS e " +
            "WHERE e.initiator.id = ?1 ")
    List<Event> getUserEvents(Integer userId, Pageable pageable);
}
