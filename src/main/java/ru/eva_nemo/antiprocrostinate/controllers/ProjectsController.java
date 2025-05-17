package ru.eva_nemo.antiprocrostinate.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eva_nemo.antiprocrostinate.dto.projects.ProjectDto;
import ru.eva_nemo.antiprocrostinate.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@SecurityRequirement(name="Authorization")
public class ProjectsController {

  private final ProjectService projectService;

  @PostMapping
  public ProjectDto create(@RequestBody @Valid ProjectDto projectDto) {
      System.out.println(projectDto.name());
    return projectService.create(projectDto);
  }

  @PatchMapping
  public ProjectDto edit(@RequestBody @Valid ProjectDto projectDto) {
    return projectService.update(projectDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id){
    projectService.delete(id);
  };
  @GetMapping
  public List<ProjectDto> get(){
    return projectService.getProjects();
  }

  @GetMapping("/{id}")
  public ProjectDto getById(@PathVariable("id") UUID id){
    return projectService.getById(id);
  }
}
