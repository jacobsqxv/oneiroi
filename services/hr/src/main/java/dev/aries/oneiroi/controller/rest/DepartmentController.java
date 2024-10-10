package dev.aries.oneiroi.controller.rest;

import java.util.List;

import dev.aries.oneiroi.dto.DepartmentResponse;
import dev.aries.oneiroi.dto.DepartmentRequest;
import dev.aries.oneiroi.service.departmentservice.DepartmentService;
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

	@PostMapping
	public ResponseEntity<DepartmentResponse> addNewDepartment(@RequestBody DepartmentRequest request) {
		return new ResponseEntity<>(departmentService.addNewDepartment(request), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable Integer id) {
		return ResponseEntity.ok(departmentService.getDepartmentResponse(id));
	}

	@PutMapping("/{id}/info")
	public ResponseEntity<DepartmentResponse> updateDepartmentInfo(@PathVariable Integer id, @RequestBody DepartmentRequest request) {
		return ResponseEntity.ok(departmentService.updateInfo(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
		return new ResponseEntity<>(departmentService.deleteDepartment(id), HttpStatus.NO_CONTENT);
	}
}
