package dev.aries.oneiroi.controller.graphql;

import java.time.OffsetDateTime;
import java.util.List;

import dev.aries.oneiroi.dto.DepartmentRequest;
import dev.aries.oneiroi.dto.DepartmentResponse;
import dev.aries.oneiroi.dto.GenericResponse;
import dev.aries.oneiroi.queryresolver.CustomQueryResolver;
import dev.aries.oneiroi.service.departmentservice.DepartmentService;
import lombok.RequiredArgsConstructor;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DepartmentResolver {
	private final DepartmentService departmentService;
	private final CustomQueryResolver queryResolver;

	@QueryMapping
	public List<DepartmentResponse> getAllDepartments() {
		return departmentService.getAllDepartments();
	}

	@QueryMapping
	public DepartmentResponse getDepartment(@Argument Integer id) {
		return departmentService.getDepartmentResponse(id);
	}

	@MutationMapping
	public DepartmentResponse addNewDepartment(@Argument DepartmentRequest request) {
		return departmentService.addNewDepartment(request);
	}

	@MutationMapping
	public DepartmentResponse updateDepartmentInfo(@Argument Integer id, @Argument DepartmentRequest request) {
		return departmentService.updateInfo(id, request);
	}

	@MutationMapping
	public GenericResponse deleteDepartment(@Argument Integer id) {
		return departmentService.deleteDepartment(id);
	}

	@SchemaMapping(typeName = "DepartmentResponse")
	public OffsetDateTime createdAt(DepartmentResponse dept) {
		return queryResolver.convertLocalDateTime(dept.createdAt());
	}
}
