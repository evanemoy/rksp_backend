package ru.eva_nemo.antiprocrostinate.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.eva_nemo.antiprocrostinate.dto.tasks.TaskDto;
import ru.eva_nemo.antiprocrostinate.models.TaskEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper extends IMapper<TaskDto, TaskEntity> {
    @Override
    @Mapping(target = "projectId",
        source = "project.projectId")
    TaskDto mapToDto(TaskEntity entity);

    @Override
    TaskEntity mapToEntity(TaskDto dto);
}