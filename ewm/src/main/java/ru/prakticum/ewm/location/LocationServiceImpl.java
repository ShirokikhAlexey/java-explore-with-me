package ru.prakticum.ewm.location;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.prakticum.ewm.exception.NotFoundException;
import ru.prakticum.ewm.location.model.Location;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;

    @Override
    public List<Location> get(Float lat, Float lon, Integer from, Integer size) {
        return repository.getByCoordinates(lat, lon, PageRequest.of(from / size, size));
    }

    @Override
    public Location create(Location location) {
        return repository.save(location);
    }

    @Override
    public Location update(Integer id, Location location) {
        Optional<Location> locationDbOptional = repository.findById(id);
        if (locationDbOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Location locationDb = locationDbOptional.get();
        if (location.getDescription() != null) {
            locationDb.setDescription(location.getDescription());
        }
        return repository.save(locationDb);
    }

    @Override
    public List<Location> search(Float lat, Float lon, Float radius, Integer from, Integer size) {
        return repository.search(lat, lon, radius, PageRequest.of(from / size, size));
    }
}
