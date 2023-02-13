package ru.prakticum.ewm.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.prakticum.ewm.event.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query(value = "SELECT r " +
            "FROM Request AS r " +
            "WHERE r.event.id = ?1 ")
    List<Request> getEventRequests(Integer eventId);

    @Query(value = "SELECT r " +
            "FROM Request AS r " +
            "WHERE r.event.id = ?1 " +
            "AND r.status = 'PENDING' ")
    List<Request> getEventRequestsPending(Integer eventId);

    @Query(value = "SELECT r " +
            "FROM Request AS r " +
            "WHERE r.user.id = ?1 ")
    List<Request> getUserRequests(Integer userId);
}
