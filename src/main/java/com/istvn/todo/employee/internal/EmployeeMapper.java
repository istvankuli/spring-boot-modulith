package com.istvn.todo.employee.internal;

import java.util.List;

import org.mapstruct.Mapper;

import com.istvn.todo.employee.EmployeeDTO;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	Employee toEmployeeEnity(EmployeeDTO employeeDTO);
	
	List<Employee> toEmployeeEntityList(List<EmployeeDTO> employeeDTOs);
	
	EmployeeDTO toEmployeeDTO(Employee employee);
	
	List<EmployeeDTO> toEmployeeDTOList(List<Employee> employees);
}
