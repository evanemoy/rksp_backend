package ru.eva_nemo.antiprocrostinate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.eva_nemo.antiprocrostinate.models.TaskEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository
        extends JpaRepository<TaskEntity, UUID> {

    @Query("SELECT t FROM TaskEntity t WHERE t.project.id = :projectId")
    List<TaskEntity> findByProjectId(@Param("projectId") UUID projectId);
}
