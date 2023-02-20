package ru.prakticum.ewm.location;

import org.springframework.stereotype.Service;
import ru.prakticum.ewm.location.model.Location;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class LocationRepositoryCustomImpl implements LocationRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Location> get(Float lat, Float lon, Integer from, Integer size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> criteriaQuery = cb.createQuery(Location.class);
        Root<Location> locationRoot = criteriaQuery.from(Location.class);
        criteriaQuery.select(locationRoot);

        Predicate predicate = cb.and();
        if (lat != null) {
            Predicate predicateLat = cb.equal(locationRoot.get("lat"), lat);
            predicate = cb.and(predicate, predicateLat);
        }
        if (lon != null) {
            Predicate predicateLon= cb.equal(locationRoot.get("lon"), lon);
            predicate = cb.and(predicate, predicateLon);
        }

        criteriaQuery.where(predicate);
        return em.createQuery(criteriaQuery).setFirstResult(from).setMaxResults(size).getResultList();
    }
}
