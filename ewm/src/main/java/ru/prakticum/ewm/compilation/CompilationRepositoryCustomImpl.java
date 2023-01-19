package ru.prakticum.ewm.compilation;

import org.springframework.stereotype.Service;
import ru.prakticum.ewm.compilation.model.Compilation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class CompilationRepositoryCustomImpl implements CompilationRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Compilation> searchCompilations(Boolean pinned, Integer from, Integer size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Compilation> criteriaQuery = cb.createQuery(Compilation.class);
        Root<Compilation> root = criteriaQuery.from(Compilation.class);
        criteriaQuery.select(root);

        Predicate predicatePinned = cb.equal(root.get("show_on_main"), pinned);
        Predicate predicate = cb.and();
        if (pinned != null){
            predicate = cb.and(predicate, predicatePinned);
        }
        criteriaQuery.where(predicate);
        return em.createQuery(criteriaQuery).setFirstResult(from).setMaxResults(size).getResultList();
    }
}
