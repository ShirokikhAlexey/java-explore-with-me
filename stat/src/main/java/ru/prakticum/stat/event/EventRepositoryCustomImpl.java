package ru.prakticum.stat.event;

import org.springframework.stereotype.Service;
import ru.prakticum.stat.event.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Event> getStatisticsWithUris(LocalDateTime start, LocalDateTime end, List<String> uris,
                                             Boolean distinct) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = cb.createQuery(Event.class);
        Root<Event> root = criteriaQuery.from(Event.class);
        criteriaQuery.select(root);
        Predicate predicate = cb.and();
        predicate = cb.and(predicate, cb.between(root.get("timestamp"), start, end));

        if (uris != null && !uris.isEmpty()) {
            Predicate predicateUris = cb.or();
            for (String uri : uris) {
                predicateUris = cb.or(predicateUris, cb.like(root.get("uri"), uri));
            }
            predicate = cb.and(predicate, predicateUris);
        }

        criteriaQuery.where(predicate).distinct(distinct);
        return em.createQuery(criteriaQuery).getResultList();
    }
}
