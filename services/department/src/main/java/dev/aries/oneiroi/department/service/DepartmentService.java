package dev.aries.oneiroi.department.service;

import java.util.List;

import dev.aries.oneiroi.department.dto.DepartmentRequest;
import dev.aries.oneiroi.department.dto.DepartmentResponse;
import dev.aries.oneiroi.department.model.Department;

public interface DepartmentService {
	Department addNewDepartment(DepartmentRequest request);

	List<DepartmentResponse> getAllDepartments();

	Department getDepartment(Integer id);

	Department updateInfo(Integer id, DepartmentRequest request);

	Void removeEmployeeFromDepartment(Integer id, Integer employeeId);

	Void deleteDepartment(Integer id);
}
