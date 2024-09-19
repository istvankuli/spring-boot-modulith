package com.istvn.todo.assignment.internal;

import java.util.List;

import org.mapstruct.Mapper;

import com.istvn.todo.assignment.AssignmentDTO;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
	Assignment toAssignmentEntity(AssignmentDTO assignmentDTO);
	
	List<Assignment> toAssignmentEnityList(List<AssignmentDTO> assignmentDTOs);
	
	AssignmentDTO toAssignmentDTO(Assignment assignment);
	
	List<AssignmentDTO> toAssignmentDTOList(List<Assignment> assignments);
}
