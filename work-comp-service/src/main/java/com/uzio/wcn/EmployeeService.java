package com.uzio.wcn;

import org.springframework.stereotype.Service;

import com.uzio.wcn.exception.EmployeeServiceException;

@Service
public class EmployeeService {

	//return employee object
	public Employee getEmployee() throws EmployeeServiceException {
		Employee emp = new Employee();
		emp.setName("Ankur");
		emp.setDepartment("Engg");
		emp.setId(1);

		return emp;
	}

    //return employee as null
	public Employee getEmployeeNull() throws EmployeeServiceException {

		return null;
	}

    //throw exception
	public Employee getEmployeeException() throws EmployeeServiceException {

		throw new EmployeeServiceException();
	}

}