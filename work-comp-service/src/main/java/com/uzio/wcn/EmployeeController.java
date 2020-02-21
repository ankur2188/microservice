package com.uzio.wcn;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.uzio.wcn.exception.EmployeeServiceException;
import com.uzio.wcn.exception.ResourceNotFoundException;

@RestController
public class EmployeeController {
	
	private static final Logger LOG = Logger.getLogger(EmployeeController.class.getName());
	
	@Autowired	
	private EmployeeService employeeService;
	

	@RequestMapping(value = "/employee/{id}")
	@HystrixCommand(fallbackMethod = "fallBackGetEmployee")
	public Employee getEmployee(@PathVariable("id") String id) {
		LOG.log(Level.ALL, "Employee controller method entry");
		if (id.equalsIgnoreCase("2")) {
			throw new RuntimeException();
		}
		LOG.log(Level.ALL, "Employee controller method exit");
		return new Employee(1, "Ankur", "Engg");
	}

	public Employee fallBackGetEmployee(String id) {
		LOG.log(Level.ALL, "Employee controller method entry fallback");
		return new Employee(2, "Ankur FallBack", "Engg FallBack");
	}

	@RequestMapping(value = "/employeeFeign/{id}")
	public Employee getEmployeeFeign(@PathVariable("id") String id)
			throws EmployeeServiceException, ResourceNotFoundException {
		LOG.log(Level.ALL, "Employee controller feign method entry");
		if (id.equalsIgnoreCase("11")) {
			try {
				Employee emp = employeeService.getEmployeeNull();
				if (emp == null) {
					throw new ResourceNotFoundException("Employee not found");
				}
				return emp;
			} catch (EmployeeServiceException e) {
				throw new EmployeeServiceException("Internal Server Exception while getting exception");
			}
		} else if (id.equalsIgnoreCase("22")) {
			try {
				Employee emp = employeeService.getEmployeeException();
				if (emp == null) {
					throw new ResourceNotFoundException("Employee not found");
				}
				return emp;
			} catch (EmployeeServiceException e) {
				throw new EmployeeServiceException("Internal Server Exception while getting exception");
			}
		}
		LOG.log(Level.ALL, "Employee controller feign method exit");
		return new Employee(Integer.valueOf(id), "Ankur Feign", "Engg Feign");
	}
}
