package dev.aries.oneiroi.controller.graphql;

import java.time.OffsetDateTime;
import java.util.List;

import dev.aries.oneiroi.dto.DepartmentResponse;
import dev.aries.oneiroi.dto.EmployeeRequest;
import dev.aries.oneiroi.dto.EmployeeResponse;
import dev.aries.oneiroi.dto.GenericResponse;
import dev.aries.oneiroi.dto.RankChangeRequest;
import dev.aries.oneiroi.dto.TransferRequest;
import dev.aries.oneiroi.queryresolver.CustomQueryResolver;
import dev.aries.oneiroi.service.employeeservice.EmployeeService;
import lombok.RequiredArgsConstructor;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class EmployeeResolver {
	private final EmployeeService employeeService;
	private final CustomQueryResolver queryResolver;

	@QueryMapping
	public List<EmployeeResponse> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@QueryMapping
	public EmployeeResponse getEmployee(@Argument Integer id) {
		return employeeService.getEmployeeResponse(id);
	}

	@MutationMapping
	public EmployeeResponse addNewEmployee(@Argument EmployeeRequest request) {
		return employeeService.addNewEmployee(request);
	}

	@MutationMapping
	public EmployeeResponse updateEmployeeInfo(@Argument Integer id, @Argument EmployeeRequest request) {
		return employeeService.updateEmployeeInfo(id, request);
	}

	@MutationMapping
	public List<EmployeeResponse> updateEmployeesRanks(@Argument RankChangeRequest request) {
		return employeeService.updateEmployeesRanks(request);
	}

	@MutationMapping
	public List<EmployeeResponse> transferEmployees(@Argument TransferRequest request) {
		return employeeService.transferEmployees(request);
	}

	@MutationMapping
	public GenericResponse terminateEmployees(@Argument List<Integer> employeeIds) {
		return employeeService.terminateEmployees(employeeIds);
	}

	@SchemaMapping(typeName = "EmployeeResponse")
	public OffsetDateTime hireDate(EmployeeResponse employee) {
		return queryResolver.convertLocalDateTime(employee.hireDate());
	}
}
