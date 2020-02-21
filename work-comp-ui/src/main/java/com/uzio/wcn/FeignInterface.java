package com.uzio.wcn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "WORKCOMPSERVICE")
public interface FeignInterface {
	@RequestMapping(method = RequestMethod.GET, value = "/employeeFeign/{id}")
	public Employee getData(@PathVariable("id") String id);

}
