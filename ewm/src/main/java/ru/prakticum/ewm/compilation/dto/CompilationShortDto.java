package ru.prakticum.ewm.compilation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CompilationShortDto {
    private Integer id;
    private String title;
    private Boolean pinned;

    @JsonProperty("events")
    private List<Integer> events;

    public CompilationShortDto(String title, Boolean pinned, List<Integer> events) {
        this.title = title;
        this.pinned = pinned;
        this.events = events;
    }
}
