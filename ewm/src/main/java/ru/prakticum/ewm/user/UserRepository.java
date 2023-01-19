package ru.prakticum.ewm.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.prakticum.ewm.event.model.Event;
import ru.prakticum.ewm.user.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    @Query(value = "select u " +
            "from User as u " +
            "where u.id in ?1 ")
    List<User> searchUsers(List<Integer> users, Pageable pageable);

    @Query(value = "select u " +
            "from User as u ")
    List<User> searchUsers(Pageable pageable);
}
