package ru.prakticum.ewm.compilation.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.prakticum.ewm.compilation.model.Compilation;
import ru.prakticum.ewm.event.dto.EventMapper;
import ru.prakticum.ewm.event.dto.EventShortDto;
import ru.prakticum.ewm.event.model.Event;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompilationDtoMapper {
    public static CompilationDto toDto(Compilation compilation) {
        List<EventShortDto> events = new ArrayList<>();
        for (Event e : compilation.getEvents()) {
            events.add(EventMapper.toDtoShort(e, null));
        }
        return new CompilationDto(compilation.getId(), compilation.getName(), compilation.getShowOnMain(), events);
    }

    public static Compilation fromDto(CompilationDto compilationDto, List<Event> events) {
        try {
            return new Compilation(compilationDto.getId(), compilationDto.getTitle(), compilationDto.getPinned(),
                    events);
        } catch (NullPointerException e) {
            throw new ValidationException();
        }
    }

    public static Compilation update(Compilation compilation, CompilationShortDto shortDto, List<Event> events) {
        compilation.setEvents(events);
        if (shortDto.getPinned() != null) {
            compilation.setShowOnMain(shortDto.getPinned());
        }
        if (shortDto.getTitle() != null) {
            compilation.setName(shortDto.getTitle());
        }
        return compilation;
    }
}
