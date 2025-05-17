package ru.eva_nemo.antiprocrostinate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.eva_nemo.antiprocrostinate.dto.projects.ProjectDto;
import ru.eva_nemo.antiprocrostinate.exception.NotFoundException;
import ru.eva_nemo.antiprocrostinate.mappers.IMapper;
import ru.eva_nemo.antiprocrostinate.models.ProjectEntity;
import ru.eva_nemo.antiprocrostinate.models.UserEntity;
import ru.eva_nemo.antiprocrostinate.repository.ProjectRepository;
import ru.eva_nemo.antiprocrostinate.repository.UserRepository;
import ru.eva_nemo.antiprocrostinate.utils.security.SecurityContextHolderUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final IMapper<ProjectDto, ProjectEntity> mapper;

  public ProjectDto create(ProjectDto dto) {
    var userId = SecurityContextHolderUtils.getUser().getUserId();
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("user with id: " + userId + " wasn't found"));

    ProjectEntity projectEntity = ProjectEntity.builder()
        .name(dto.name())
        .owner(user)
        .build();

    userRepository.save(user);
    return mapper.mapToDto(projectRepository.save(projectEntity));
  }

  public ProjectDto update(ProjectDto dto) {
    var project = projectRepository.findById(dto.projectId())
        .orElseThrow(() -> projectNotFoundException(dto.projectId()));
    project.setName(dto.name());
    return mapper.mapToDto(projectRepository.save(project));
  }

  public void delete(UUID projectId) {
    var project = projectRepository.findById(projectId)
        .orElseThrow(() -> projectNotFoundException(projectId));
    projectRepository.delete(project);
  }

  public List<ProjectDto> getProjects() {
    var userId = SecurityContextHolderUtils.getUser().getUserId();
    List<ProjectEntity> projects = projectRepository.findByUserId(userId);
    return projects.stream().map(mapper::mapToDto).collect(Collectors.toList());
  }

  public ProjectDto getById(UUID id) {
    return mapper.mapToDto(projectRepository.findById(id).orElseThrow(() -> projectNotFoundException(id)));
  }

  private NotFoundException projectNotFoundException(UUID projectId) {
    return new NotFoundException("project with id: " + projectId + " wasn't found");
  }
}
