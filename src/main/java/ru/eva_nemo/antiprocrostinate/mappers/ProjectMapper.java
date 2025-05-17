package ru.eva_nemo.antiprocrostinate.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.eva_nemo.antiprocrostinate.dto.projects.ProjectDto;
import ru.eva_nemo.antiprocrostinate.models.ProjectEntity;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper extends IMapper<ProjectDto, ProjectEntity> {
  @Override
  ProjectDto mapToDto(ProjectEntity entity);
  @Override
  ProjectEntity mapToEntity(ProjectDto dto);

}
