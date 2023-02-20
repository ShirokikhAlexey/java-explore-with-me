package ru.prakticum.ewm.location;

import ru.prakticum.ewm.location.model.Location;

import java.util.List;

public interface LocationRepositoryCustom {

    List<Location> get(Float lat, Float lon, Integer from, Integer size);
}
