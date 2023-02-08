package ru.prakticum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.prakticum.ewm.event.model.RequestStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class PatchRequestDto {
    private List<Integer> requestIds;
    private RequestStatus status;
}
