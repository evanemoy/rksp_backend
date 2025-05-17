package ru.eva_nemo.antiprocrostinate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.eva_nemo.antiprocrostinate.models.ProjectEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
  @Query("SELECT p FROM ProjectEntity p WHERE p.owner.id = :userId")
  List<ProjectEntity> findByUserId(@Param("userId") UUID userId);
}
