package ru.eva_nemo.antiprocrostinate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.eva_nemo.antiprocrostinate.models.enums.TaskPriority;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID taskId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    private ProjectEntity project;
    private LocalDateTime issued;
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    private Integer hoursSpent;
}
