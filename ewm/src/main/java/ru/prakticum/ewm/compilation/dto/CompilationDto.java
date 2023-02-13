package ru.prakticum.ewm.compilation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.prakticum.ewm.event.dto.EventShortDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private Integer id;
    private String title;
    private Boolean pinned;

    @JsonProperty("events")
    private List<EventShortDto> events;

    public CompilationDto(Integer id, String title, Boolean pinned) {
        this.id = id;
        this.title = title;
        this.pinned = pinned;
    }
}
