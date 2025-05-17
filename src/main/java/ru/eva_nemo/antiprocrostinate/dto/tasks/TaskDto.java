package ru.eva_nemo.antiprocrostinate.dto.tasks;

import org.springframework.format.annotation.DateTimeFormat;
import ru.eva_nemo.antiprocrostinate.models.enums.TaskPriority;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID taskId,
        UUID projectId,
        String title,
        String description,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime deadline,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime issued,
        TaskPriority priority,
        Integer hoursSpent) {
}
