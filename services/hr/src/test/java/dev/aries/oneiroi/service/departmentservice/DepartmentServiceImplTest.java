package dev.aries.oneiroi.service.departmentservice;

import java.util.List;
import java.util.Optional;

import dev.aries.oneiroi.TestDataFactory;
import dev.aries.oneiroi.dto.DepartmentRequest;
import dev.aries.oneiroi.dto.DepartmentResponse;
import dev.aries.oneiroi.model.Department;
import dev.aries.oneiroi.model.DepartmentStatus;
import dev.aries.oneiroi.repository.DepartmentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DepartmentServiceImplTest {
	@InjectMocks
	private DepartmentServiceImpl departmentService;

	@Mock
	private DepartmentRepository departmentRepo;
	@Captor
	private ArgumentCaptor<Department> deptArgCaptor;

	private DepartmentRequest deptRequest;

	@BeforeEach
	void setUp() {
		deptRequest = TestDataFactory.deptRequest();
	}

	@Test
	@DisplayName("Add new department successfully")
	void testAddNewDepartment() {
		departmentService.addNewDepartment(deptRequest);

		verify(departmentRepo).save(deptArgCaptor.capture());
		Department actual = deptArgCaptor.getValue();
		assertThat(actual).isNotNull();
		assertThat(actual.getName()).isEqualTo(deptRequest.name());
	}

	@Test
	@DisplayName("Fail to add department when department with same name already exists")
	void testAddDepartmentWithExistingName() {
		Department newDept = new Department();
		when(departmentRepo.findByName(deptRequest.name())).thenReturn(Optional.of(newDept));

		Exception actual = assertThrows(EntityExistsException.class,
				() -> departmentService.addNewDepartment(deptRequest));

		assertThat(actual).hasMessage("Department with same name already exists");
		verify(departmentRepo, times(1)).findByName(deptRequest.name());
		verify(departmentRepo, never()).save(any());

	}

	@Test
	@DisplayName("Get all departments successfully")
	void testGetAllDepartments() {
		List<Department> expected = TestDataFactory.departments();
		when(departmentRepo.findAll()).thenReturn(expected);

		List<DepartmentResponse> actual = departmentService.getAllDepartments();

		verify(departmentRepo).findAll();
		assertThat(actual)
				.isNotNull()
				.hasSameSizeAs(expected);
		assertThat(actual.getFirst().id()).isEqualTo(expected.getFirst().getId());
		assertThat(actual.getLast().id()).isEqualTo(expected.getLast().getId());
	}

	@Test
	@DisplayName("Get department by ID that exists")
	void testGetDepartmentByIdExists() {
		Department expected = TestDataFactory.newDept();
		when(departmentRepo.findById(1)).thenReturn(Optional.of(expected));

		Department actual = departmentService.getDepartment(1);

		verify(departmentRepo, times(1)).findById(1);
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isEqualTo(expected.getId());
	}

	@Test
	@DisplayName("Get department by ID that does not exist")
	void testGetDepartmentByIdDoesNotExist() {
		when(departmentRepo.findById(1)).thenReturn(Optional.empty());

		Exception actual = assertThrows(EntityNotFoundException.class,
				() -> departmentService.getDepartment(1));

		verify(departmentRepo, times(1)).findById(1);
		assertThat(actual)
				.isNotNull()
				.hasMessage("Department not found");
	}

	@Test
	@DisplayName("Update department that exists")
	void testUpdateDepartmentExists() {
		Department expected = TestDataFactory.newDept();
		when(departmentRepo.findById(1)).thenReturn(Optional.of(expected));
		departmentService.updateInfo(1, deptRequest);


		verify(departmentRepo, times(1)).findById(1);
		verify(departmentRepo).save(deptArgCaptor.capture());
		Department actual = deptArgCaptor.getValue();

		assertThat(actual.getId()).isEqualTo(expected.getId());
		assertThat(actual.getName()).isEqualTo(deptRequest.name());
	}

	@Test
	@DisplayName("Update department that does not exist")
	void testUpdateDepartmentThatDoesNotExist() {
		when(departmentRepo.findById(1)).thenReturn(Optional.empty());

		Exception actual = assertThrows(EntityNotFoundException.class,
				() -> departmentService.updateInfo(1, deptRequest));
		assertThat(actual)
				.isNotNull()
				.hasMessage("Department not found");
		verify(departmentRepo, never()).save(any());
	}

	@Test
	@DisplayName("Delete department that exists")
	void testDeleteDepartmentThatExists() {
		Department dept = TestDataFactory.newDept();
		when(departmentRepo.findById(1)).thenReturn(Optional.of(dept));

		departmentService.deleteDepartment(1);
		verify(departmentRepo).save(deptArgCaptor.capture());
		Department actual = deptArgCaptor.getValue();
		assertThat(actual.getId()).isEqualTo(dept.getId());
		assertThat(actual.getStatus()).isEqualTo(DepartmentStatus.DELETED);
	}

	@Test
	@DisplayName("Delete department that does not exist")
	void testDeleteDepartmentThatDoesNotExist() {
		when(departmentRepo.findById(1)).thenReturn(Optional.empty());

		Exception actual = assertThrows(EntityNotFoundException.class,
				() -> departmentService.deleteDepartment(1));
		assertThat(actual)
				.isNotNull()
				.hasMessage("Department not found");
		verify(departmentRepo, never()).save(any());
	}
}
