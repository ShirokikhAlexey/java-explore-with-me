package ru.prakticum.ewm.compilation.dto;

import ru.prakticum.ewm.compilation.model.Compilation;
import ru.prakticum.ewm.event.dto.EventMapper;
import ru.prakticum.ewm.event.dto.EventShortDto;
import ru.prakticum.ewm.event.model.Event;

import java.util.ArrayList;
import java.util.List;

public class CompilationDtoMapper {
    public static CompilationDto toDto(Compilation compilation) {
        List<EventShortDto> events = new ArrayList<>();
        for (Event e : compilation.getEvents()) {
            events.add(EventMapper.toDtoShort(e, null));
        }
        return new CompilationDto(compilation.getId(), compilation.getName(), compilation.getShow_on_main(), events);
    }

    public static Compilation fromDto(CompilationDto compilationDto, List<Event> events) {

        return new Compilation(compilationDto.getId(), compilationDto.getTitle(), compilationDto.getPinned(),
                events);
    }
}
