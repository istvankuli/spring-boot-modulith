package com.istvn.todo.employee;

import java.util.List;

public interface EmployeeService {
	EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
	
	String deleteEmployee(String id);
	
	EmployeeDTO getEmployee(String id);
	
	List<EmployeeDTO> listEmployees();
}
