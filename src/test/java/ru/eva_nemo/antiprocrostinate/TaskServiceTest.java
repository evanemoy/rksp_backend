package ru.eva_nemo.antiprocrostinate;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.eva_nemo.antiprocrostinate.dto.tasks.TaskDto;
import ru.eva_nemo.antiprocrostinate.exception.NotFoundException;
import ru.eva_nemo.antiprocrostinate.mappers.IMapper;
import ru.eva_nemo.antiprocrostinate.models.ProjectEntity;
import ru.eva_nemo.antiprocrostinate.models.TaskEntity;
import ru.eva_nemo.antiprocrostinate.models.enums.TaskPriority;
import ru.eva_nemo.antiprocrostinate.repository.ProjectRepository;
import ru.eva_nemo.antiprocrostinate.repository.TaskRepository;
import ru.eva_nemo.antiprocrostinate.service.TaskService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private IMapper<TaskDto, TaskEntity> mapper;

    @InjectMocks
    private TaskService taskService;

    private final UUID projectId = UUID.randomUUID();
    private final UUID taskId = UUID.randomUUID();
    private final LocalDateTime now = LocalDateTime.now();
    private final LocalDateTime deadline = now.plusDays(1);

    @Test
    void create_ShouldCreateTask_WhenProjectExists() {
        // Arrange
        TaskDto inputDto = new TaskDto(taskId, projectId, "Test Task", "Description", deadline, LocalDateTime.now(),TaskPriority.NORMAL, 0);
        ProjectEntity project = new ProjectEntity();
        project.setProjectId(projectId);
        project.setTasks(new HashSet<>());
        TaskEntity savedTask = TaskEntity.builder()
                .taskId(taskId)
                .project(project)
                .title("Test Task")
                .description("Description")
                .issued(now)
                .deadline(deadline)
                .priority(TaskPriority.CRITICAL)
                .hoursSpent(0)
                .build();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(savedTask);
        when(mapper.mapToDto(savedTask)).thenReturn(inputDto);

        // Act
        TaskDto result = taskService.create(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(taskId, result.taskId());
        assertEquals(projectId, result.projectId());
        verify(projectRepository).save(project);
        verify(taskRepository).save(any(TaskEntity.class));
    }

    @Test
    void create_ShouldThrowNotFoundException_WhenProjectNotExists() {
        // Arrange
        TaskDto inputDto = new TaskDto(taskId, projectId, "Test Task", "Description", deadline, LocalDateTime.now(), TaskPriority.NORMAL, 0);
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taskService.create(inputDto));
        verify(projectRepository, never()).save(any());
        verify(taskRepository, never()).save(any());
    }


    @Test
    void update_ShouldThrowNotFoundException_WhenTaskNotExists() {
        // Arrange
        TaskDto inputDto = new TaskDto(taskId, projectId, "Test Task", "Description", deadline, LocalDateTime.now(), TaskPriority.NORMAL, 0);
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taskService.update(inputDto));
        verify(taskRepository, never()).save(any());
    }


    @Test
    void delete_ShouldDeleteTask_WhenTaskExists() {
        // Arrange
        TaskEntity task = new TaskEntity();
        task.setTaskId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        taskService.delete(taskId);

        // Assert
        verify(taskRepository).delete(task);
    }

    @Test
    void delete_ShouldThrowNotFoundException_WhenTaskNotExists() {
        // Arrange
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taskService.delete(taskId));
        verify(taskRepository, never()).delete(any());
    }

    @Test
    void getById_ShouldReturnTask_WhenTaskExists() {
        // Arrange
        TaskEntity task = TaskEntity.builder()
                .taskId(taskId)
                .title("Test Task")
                .description("Description")
                .deadline(deadline)
                .priority(TaskPriority.NORMAL)
                .hoursSpent(0)
                .build();

        TaskDto expectedDto = new TaskDto(taskId, projectId, "Test Task", "Description", deadline, LocalDateTime.now(), TaskPriority.NORMAL, 0);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(mapper.mapToDto(task)).thenReturn(expectedDto);

        // Act
        TaskDto result = taskService.getById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(taskId, result.taskId());
        assertEquals("Test Task", result.title());
    }

    @Test
    void getById_ShouldThrowNotFoundException_WhenTaskNotExists() {
        // Arrange
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taskService.getById(taskId));
    }

    @Test
    void getByProjectId_ShouldReturnTasksList_WhenProjectHasTasks() {
        // Arrange
        TaskEntity task1 = TaskEntity.builder().taskId(taskId).title("Task 1").build();
        TaskEntity task2 = TaskEntity.builder().taskId(UUID.randomUUID()).title("Task 2").build();
        List<TaskEntity> tasks = List.of(task1, task2);

        TaskDto dto1 = new TaskDto(taskId, projectId, "Task 1", null, null, LocalDateTime.now(), TaskPriority.NORMAL, 0);
        TaskDto dto2 = new TaskDto(task2.getTaskId(), projectId, "Task 2", null, null, LocalDateTime.now(),TaskPriority.NORMAL, 0);

        when(taskRepository.findByProjectId(projectId)).thenReturn(tasks);
        when(mapper.mapToDto(task1)).thenReturn(dto1);
        when(mapper.mapToDto(task2)).thenReturn(dto2);

        // Act
        List<TaskDto> result = taskService.getByProjectId(projectId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).title());
        assertEquals("Task 2", result.get(1).title());
    }

    @Test
    void getByProjectId_ShouldReturnEmptyList_WhenProjectHasNoTasks() {
        // Arrange
        when(taskRepository.findByProjectId(projectId)).thenReturn(List.of());

        // Act
        List<TaskDto> result = taskService.getByProjectId(projectId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
