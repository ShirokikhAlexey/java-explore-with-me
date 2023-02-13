package ru.prakticum.ewm.category;

import org.springframework.stereotype.Service;
import ru.prakticum.ewm.category.model.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Category> searchCategories(Integer from, Integer size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = cb.createQuery(Category.class);
        Root<Category> root = criteriaQuery.from(Category.class);
        criteriaQuery.select(root);
        return em.createQuery(criteriaQuery).setFirstResult(from).setMaxResults(size).getResultList();
    }
}
