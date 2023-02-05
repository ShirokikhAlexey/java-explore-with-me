package ru.prakticum.ewm.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class StatDto {
    private String path;
    private String ip;
    private LocalDateTime timestamp;
    private String app;

    public StatDto(String path, String ip, LocalDateTime timestamp, String app) {
        this.path = path;
        this.ip = ip;
        this.timestamp = timestamp;
        this.app = app;
    }
}
