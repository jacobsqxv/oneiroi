package dev.aries.oneiroi.controller.graphql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.aries.oneiroi.TestDataFactory;
import dev.aries.oneiroi.config.GraphQlConfig;
import dev.aries.oneiroi.dto.DepartmentResponse;
import dev.aries.oneiroi.model.Department;
import dev.aries.oneiroi.queryresolver.CustomQueryResolver;
import dev.aries.oneiroi.repository.DepartmentRepository;
import dev.aries.oneiroi.repository.EmployeeRepository;
import dev.aries.oneiroi.service.departmentservice.DepartmentServiceImpl;
import dev.aries.oneiroi.service.employeeservice.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import static graphql.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest
@Import({DepartmentServiceImpl.class, CustomQueryResolver.class,
		EmployeeServiceImpl.class, GraphQlConfig.class})
@ActiveProfiles("native")
class DepartmentResolverTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@MockBean
	private DepartmentRepository departmentRepo;
	@MockBean
	private EmployeeRepository employeeRepo;

	private DepartmentResponse response;
	private Department dept;

	@BeforeEach
	void setUp() {
		response = TestDataFactory.newDeptResponse("Department");
		dept = TestDataFactory.newDept();
	}

	@Test
	@DisplayName("Get a list of all departments")
	void canGetAllDepartments() {
		List<Department> deptList = TestDataFactory.departments();
		when(departmentRepo.findAll()).thenReturn(deptList);

		graphQlTester
				.documentName("queryAll")
				.execute()
				.path("getAllDepartments")
				.entityList(DepartmentResponse.class)
				.satisfies(actual -> {
					assertNotNull(actual);
					assertEquals(2, actual.size());
				});
	}

	@Test
	@DisplayName("Get a department by ID")
	void canGetDepartmentById() {
		when(departmentRepo.findById(1)).thenReturn(Optional.of(dept));

		graphQlTester
				.documentName("queryOne")
				.execute()
				.path("getDepartment")
				.entity(DepartmentResponse.class)
				.satisfies(actual -> {
					assertNotNull(actual);
					assertEquals(dept.getId(), actual.id());
				});

	}

	@Test
	@DisplayName("Add new department")
	void canAddNewDepartment() {
		when(departmentRepo.findByName(response.name())).thenReturn(Optional.empty());
		when(departmentRepo.save(any())).thenReturn(dept);

		graphQlTester
				.documentName("addOne")
				.execute()
				.path("addNewDepartment")
				.entity(DepartmentResponse.class)
				.satisfies(actual -> {
					assertNotNull(actual);
					assertEquals("Department", actual.name());
				});
	}

	@Test
	@DisplayName("Update existing department")
	void updateExistingDepartment() {

	}
}
