package com.uzio.wcn.exception;

import com.uzio.wcn.Employee;
import com.uzio.wcn.EmployeeService;

public class MainExce {
	
	public static void main(String[] args) throws ResourceNotFoundException, EmployeeServiceException {
		EmployeeService employeeService = new EmployeeService();
		try {
			Employee emp = employeeService.getEmployeeException();
			if (emp == null) {
				throw new ResourceNotFoundException("Employee not found");
			}
		} catch (EmployeeServiceException e) {
			throw new EmployeeServiceException("Internal Server Exception while getting exception");
		}
		
	}

}
