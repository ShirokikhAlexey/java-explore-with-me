package ru.prakticum.ewm.event;

import org.springframework.stereotype.Service;
import ru.prakticum.ewm.category.model.Category;
import ru.prakticum.ewm.event.model.Event;
import ru.prakticum.ewm.event.model.Request;
import ru.prakticum.ewm.event.model.RequestStatus;
import ru.prakticum.ewm.event.model.Status;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Event> search(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                              LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        return this.search(text, null, null, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
    }

    @Override
    public List<Event> search(String text, List<Integer> users, List<String> states, List<Integer> categories,
                              Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                              String sort, Integer from, Integer size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = cb.createQuery(Event.class);
        Root<Event> eventRoot = criteriaQuery.from(Event.class);
        Join<Event, Category> categoryJoin = eventRoot.join("categories", JoinType.INNER);
        criteriaQuery.select(eventRoot);

        Subquery<Long> approved = criteriaQuery.subquery(Long.class);
        Root<Request> requestRoot = approved.from(Request.class);
        approved.select(cb.count(requestRoot));
        approved.where(cb.equal(requestRoot.get("status"), RequestStatus.CONFIRMED.name()));
        approved.groupBy(requestRoot.get("event"));

        if (Objects.equals(sort, "EVENT_DATE")) {
            criteriaQuery.orderBy(cb.asc(eventRoot.get("eventDate")));
        }
        if (Objects.equals(sort, "VIEWS")) {
            criteriaQuery.orderBy(cb.desc(eventRoot.get("views")));
        }

        Predicate predicate = cb.and();
        if (text != null) {
            Predicate predicateText = cb.or(cb.like(eventRoot.get("annotation"), "%" + text + "%"),
                    cb.like(eventRoot.get("description"), "%" + text + "%"));
            predicate = cb.and(predicate, predicateText);
        }
        if (categories != null) {
            Predicate predicateCategories = categoryJoin.get("id").in(categories);
            predicate = cb.and(predicate, predicateCategories);
        }
        if (paid != null) {
            Predicate predicatePaid = cb.equal(eventRoot.get("paid"), paid);
            predicate = cb.and(predicate, predicatePaid);
        }
        if (rangeStart != null) {
            Predicate predicateRangeStart = cb.greaterThanOrEqualTo(eventRoot.get("eventDate"), rangeStart);
            predicate = cb.and(predicate, predicateRangeStart);
        }
        if (rangeEnd != null) {
            Predicate predicateRangeEnd = cb.lessThanOrEqualTo(eventRoot.get("eventDate"), rangeEnd);
            predicate = cb.and(predicate, predicateRangeEnd);
        }
        if (onlyAvailable != null && onlyAvailable) {
            Predicate predicateOnlyAvailable = cb.gt(eventRoot.get("participantLimit"), approved);
            predicate = cb.and(predicate, predicateOnlyAvailable);
        }
        if (users != null) {
            Predicate predicateUsers = eventRoot.get("initiator").get("id").in(users);
            predicate = cb.and(predicate, predicateUsers);
        }
        if (states != null) {
            List<Status> statuses = new ArrayList<>();
            for (String i : states) {
                statuses.add(Status.valueOf(i));
            }
            Predicate predicateStates = eventRoot.get("state").in(statuses);
            predicate = cb.and(predicate, predicateStates);
        }

        criteriaQuery.where(predicate);
        return em.createQuery(criteriaQuery).setFirstResult(from).setMaxResults(size).getResultList();
    }

    @Override
    public Long getApprovedCount(Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Request> root = criteriaQuery.from(Request.class);
        criteriaQuery.select(cb.count(root));
        criteriaQuery.where(cb.and(cb.equal(root.get("status"), RequestStatus.CONFIRMED),
                cb.equal(root.get("event").get("id"), id)));
        try {
            return em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }


}
