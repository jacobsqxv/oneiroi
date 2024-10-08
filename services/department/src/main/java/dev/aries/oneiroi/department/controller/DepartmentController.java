package dev.aries.oneiroi.department.controller;

import java.util.List;

import dev.aries.oneiroi.department.model.Department;
import dev.aries.oneiroi.department.dto.DepartmentRequest;
import dev.aries.oneiroi.department.dto.DepartmentResponse;
import dev.aries.oneiroi.department.service.DepartmentService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;

	@GetMapping
	public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartment(@PathVariable Integer id) {
		return ResponseEntity.ok(departmentService.getDepartment(id));
	}

	@PostMapping
	public ResponseEntity<Department> addNewDepartment(@RequestBody DepartmentRequest request) {
		return new ResponseEntity<>(departmentService.addNewDepartment(request), HttpStatus.CREATED);
	}

	@PutMapping("/{id}/info")
	public ResponseEntity<Department> updateDepartmentInfo(@PathVariable Integer id, @RequestBody DepartmentRequest request) {
		return ResponseEntity.ok(departmentService.updateInfo(id, request));
	}

	@DeleteMapping("/{id}/employees")
	public ResponseEntity<Void> removeDepartmentEmployee(@PathVariable Integer id, @RequestBody Integer employeeId) {
		return new ResponseEntity<>(departmentService.removeEmployeeFromDepartment(id, employeeId),
				HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
		return new ResponseEntity<>(departmentService.deleteDepartment(id), HttpStatus.NO_CONTENT);
	}
}
