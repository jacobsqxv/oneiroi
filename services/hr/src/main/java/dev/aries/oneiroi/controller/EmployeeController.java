package dev.aries.oneiroi.controller;

import java.util.List;

import dev.aries.oneiroi.dto.EmployeeRequest;
import dev.aries.oneiroi.dto.EmployeeResponse;
import dev.aries.oneiroi.dto.RankChangeRequest;
import dev.aries.oneiroi.dto.TransferRequest;
import dev.aries.oneiroi.model.Employee;
import dev.aries.oneiroi.service.employeeservice.EmployeeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;

	//	Used by other microservices to validate an employee by id
	@GetMapping("/validate")
	public ResponseEntity<Employee> validateEmployeeId(@RequestParam Integer id) {
		return ResponseEntity.ok(employeeService.getEmployee(id));
	}

	@PostMapping
	public ResponseEntity<EmployeeResponse> addNewEmployee(@RequestBody EmployeeRequest request) {
		return new ResponseEntity<>(employeeService.addNewEmployee(request), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeResponse(@PathVariable Integer id) {
		return ResponseEntity.ok(employeeService.getEmployeeResponse(id));
	}

	@PutMapping("/{id}/info")
	public ResponseEntity<EmployeeResponse> updateEmployeeInfo(@PathVariable Integer id, @RequestBody EmployeeRequest request) {
		return ResponseEntity.ok(employeeService.updateEmployeeInfo(id, request));
	}

	@PutMapping("/rank-updates")
	public ResponseEntity<List<EmployeeResponse>> updateEmployeeRank(@RequestBody RankChangeRequest request) {
		return ResponseEntity.ok(employeeService.updateEmployeesRanks(request));
	}

	@PutMapping("/transfers")
	public ResponseEntity<List<EmployeeResponse>> transferEmployees(@RequestBody TransferRequest request) {
		return ResponseEntity.ok(employeeService.transferEmployees(request));
	}

	@DeleteMapping("/terminate")
	public ResponseEntity<Void> terminateEmployees(@RequestBody List<Integer> employeeIds) {
		return new ResponseEntity<>(employeeService.terminateEmployees(employeeIds),
				HttpStatus.NO_CONTENT);
	}
}
