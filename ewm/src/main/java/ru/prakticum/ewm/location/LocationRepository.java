package ru.prakticum.ewm.location;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.prakticum.ewm.location.model.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer>, LocationRepositoryCustom {
    @Query(value = "SELECT l " +
            "FROM Location AS l " +
            "WHERE distance(?1, ?2, l.lat, l.lon) <= ?3 ")
    List<Location> search(Float lat, Float lon, Float radius, Pageable pageable);

    @Query(value = "SELECT l " +
            "FROM Location AS l " +
            "WHERE l.lat = ?1 AND l.lon = ?2 ")
    List<Location> getByCoordinates(Float lat, Float lon);
}
