package ru.prakticum.ewm.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.prakticum.ewm.event.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query(value = "select r " +
            "from Request as r " +
            "where r.event.id = ?1 ")
    List<Request> getEventRequests(Integer eventId);

    @Query(value = "select r " +
            "from Request as r " +
            "where r.event.id = ?1 " +
            "and r.status = 'PENDING' ")
    List<Request> getEventRequestsPending(Integer eventId);

    @Query(value = "select r " +
            "from Request as r " +
            "where r.user.id = ?1 ")
    List<Request> getUserRequests(Integer userId);
}
