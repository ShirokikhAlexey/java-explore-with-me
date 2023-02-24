package ru.prakticum.ewm.location;

import ru.prakticum.ewm.location.model.Location;

import java.util.List;

public interface LocationService {

    List<Location> get(Float lat, Float lon, Integer from, Integer size);

    Location create(Location location);

    Location update(Integer id, Location location);

    List<Location> search(Float lat, Float lon, Float radius, Integer from, Integer size);
}
