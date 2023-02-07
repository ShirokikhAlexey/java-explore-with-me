package ru.prakticum.ewm.compilation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
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
