package dev.aries.oneiroi.service.departmentservice;

import java.util.List;

import dev.aries.oneiroi.constant.ExceptionConstant;
import dev.aries.oneiroi.dto.DepartmentRequest;
import dev.aries.oneiroi.dto.DepartmentResponse;
import dev.aries.oneiroi.model.Department;
import dev.aries.oneiroi.model.DepartmentStatus;
import dev.aries.oneiroi.repository.DepartmentRepository;
import dev.aries.oneiroi.util.Util;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepo;

	@Override
	public Department getDepartment(Integer id) {
		log.info("Lookup for department: '{}'", id);
		return departmentRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstant.NOT_FOUND.getMessage(), "Department")));
	}

	@Override
	public DepartmentResponse addNewDepartment(DepartmentRequest request) {
		checkName(request.name());
		Department newDept = new Department(
				request.name(),
				request.description(),
				request.budget()
		);
		departmentRepo.save(newDept);
		log.info("New department: '{}' added", newDept.getName());
		return DepartmentResponse.basicResponse(newDept);
	}

	private void checkName(String name) {
		if (departmentRepo.findByName(name).isPresent()) {
			throw new EntityExistsException("Department with same name already exists");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<DepartmentResponse> getAllDepartments() {
		return departmentRepo.findAll().stream()
				.map(DepartmentResponse::listResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public DepartmentResponse getDepartmentResponse(Integer id) {
		Department dept = getDepartment(id);
		return DepartmentResponse.fullResponse(dept);
	}

	@Override
	@Transactional(readOnly = true)
	public DepartmentResponse updateInfo(Integer id, DepartmentRequest request) {
		Department dept = getDepartment(id);

		if (!dept.getName().equals(request.name())) {
			checkName(request.name());
		}

		Util.updateField(dept::setName, dept.getName(), request.name());
		Util.updateField(dept::setDescription, dept.getDescription(), request.description());
		Util.updateField(dept::setBudget, dept.getBudget(), request.budget());
		departmentRepo.save(dept);
		log.info("Department: '{}' updated", dept.getId());
		return DepartmentResponse.basicResponse(dept);
	}

	@Override
	public Void deleteDepartment(Integer id) {
		Department department = getDepartment(id);
		department.setStatus(DepartmentStatus.DELETED);
		departmentRepo.save(department);
		log.info("Department: '{}' deleted successfully", department.getName());
		return null;
	}
}
