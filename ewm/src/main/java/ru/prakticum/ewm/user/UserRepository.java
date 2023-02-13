package ru.prakticum.ewm.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.prakticum.ewm.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    @Query(value = "SELECT u " +
            "FROM User AS u " +
            "WHERE u.id IN ?1 ")
    List<User> searchUsers(List<Integer> users, Pageable pageable);

    @Query(value = "SELECT u " +
            "FROM User AS u ")
    List<User> searchUsers(Pageable pageable);
}
