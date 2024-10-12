package dev.aries.oneiroi.service.departmentservice;

import java.util.List;

import dev.aries.oneiroi.dto.DepartmentRequest;
import dev.aries.oneiroi.dto.DepartmentResponse;
import dev.aries.oneiroi.dto.GenericResponse;
import dev.aries.oneiroi.model.Department;

public interface DepartmentService {

	Department getDepartment(Integer id);

	DepartmentResponse addNewDepartment(DepartmentRequest request);

	List<DepartmentResponse> getAllDepartments();

	DepartmentResponse getDepartmentResponse(Integer id);

	DepartmentResponse updateInfo(Integer id, DepartmentRequest request);

	GenericResponse deleteDepartment(Integer id);
}
