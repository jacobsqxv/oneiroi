package dev.aries.oneiroi.employee;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
		name = "employee-service",
		url = "${application.config.employee-url}"
)
public interface EmployeeClient {

	@GetMapping("/{id}")
	EmployeeResponse getEmployeeResponse(@PathVariable Integer id);
}
