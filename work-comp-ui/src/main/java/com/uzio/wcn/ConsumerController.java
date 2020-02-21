package com.uzio.wcn;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
	
	private static final Logger LOG = Logger.getLogger(ConsumerController.class.getName());
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@Autowired
	private FeignInterface feignInterface;

	@RequestMapping(value = "/employeeDetails/{id}", method = RequestMethod.GET)
	public Employee getEmployeeAddDetails(@PathVariable("id") String id) {
		LOG.log(Level.ALL, "Consumer controller method entry");
		String uriToCall = "";

		// List<ServiceInstance> instances = discoveryClient.getInstances("worker-comp-service");
		// ServiceInstance serviceInstance = loadBalancerClient.choose("worker-comp-service");
		List<ServiceInstance> instances = discoveryClient.getInstances("APIGATEWAY");
		ServiceInstance serviceInstance = instances.get(0);
		String uri = serviceInstance.getUri().toString();
		uriToCall = uri + "/ui/employee/" + id;

		ResponseEntity<Employee> responseEntity = restTemplate.exchange(uriToCall, HttpMethod.GET, getHeaders(),
				Employee.class);

		System.out.println("URL called : " + uriToCall + " Response body : " + responseEntity.getBody());
		
		LOG.log(Level.ALL, "Consumer controller method exit");
		
		return responseEntity.getBody();

		// return restTemplate.getForObject(uriToCall, Employee.class);
	}

	@RequestMapping(value = "/employeeDetailsFeign/{id}", method = RequestMethod.GET)
	public Employee getEmployeeFeignDetails(@PathVariable("id") String id) {
		LOG.log(Level.ALL, "Consumer controller feign method entry");
		Employee employee = feignInterface.getData(id);
		System.out.println("Response body : " + employee);
		LOG.log(Level.ALL, "Consumer controller feign method exit");
		return employee;
	}

	private static HttpEntity<?> getHeaders() {
		LOG.log(Level.ALL, "Consumer controller get headers method entry");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		LOG.log(Level.ALL, "Consumer controller get headers method exit");
		return new HttpEntity<>(headers);
	}

}
