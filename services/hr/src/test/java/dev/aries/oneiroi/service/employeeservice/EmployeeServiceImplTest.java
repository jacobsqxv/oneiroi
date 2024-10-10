package dev.aries.oneiroi.service.employeeservice;

import java.util.List;
import java.util.Optional;

import dev.aries.oneiroi.TestDataFactory;
import dev.aries.oneiroi.dto.EmployeeRequest;
import dev.aries.oneiroi.dto.EmployeeResponse;
import dev.aries.oneiroi.dto.RankChangeRequest;
import dev.aries.oneiroi.dto.TransferRequest;
import dev.aries.oneiroi.model.Department;
import dev.aries.oneiroi.model.Employee;
import dev.aries.oneiroi.model.EmployeeStatus;
import dev.aries.oneiroi.repository.EmployeeRepository;
import dev.aries.oneiroi.service.departmentservice.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Mock
	private DepartmentService deptService;
	@Mock
	private EmployeeRepository employeeRepo;
	@Captor
	private ArgumentCaptor<Employee> empArgCaptor;
	@Captor
	private ArgumentCaptor<List<Employee>> listArgCaptor;

	@Test
	@DisplayName("Get employee by ID that exists")
	void getEmployee() {
		Employee expected = TestDataFactory.newEmployee();
		when(employeeRepo.findById(1)).thenReturn(Optional.of(expected));

		Employee actual = employeeService.getEmployee(1);

		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isEqualTo(expected.getId());
		verify(employeeRepo, times(1)).findById(1);
	}

	@Test
	@DisplayName("Get employee by ID that does not exist")
	void getEmployeeByIDDoesNotExist() {
		when(employeeRepo.findById(1)).thenReturn(Optional.empty());

		Exception actual = assertThrows(EntityNotFoundException.class,
				() -> employeeService.getEmployee(1));
		assertThat(actual)
				.isNotNull()
				.hasMessage("Employee not found");
		verify(employeeRepo, times(1)).findById(1);
	}

	@Test
	@DisplayName("Add new employee with new information that does not exist in the database")
	void addNewEmployee() {
		Department dept = TestDataFactory.newDept();
		when(deptService.getDepartment(1)).thenReturn(dept);

		EmployeeRequest request = TestDataFactory.employeeRequest();
		employeeService.addNewEmployee(request);

		verify(employeeRepo).save(empArgCaptor.capture());
		Employee actual = empArgCaptor.getValue();
		assertThat(actual).isNotNull();
		assertThat(actual.getFirstName()).isEqualTo(request.firstName());
		assertThat(actual.getDepartment().getId()).isEqualTo(dept.getId());
	}

	@Test
	@DisplayName("Get a list of all employees")
	void getAllEmployees() {
		List<Employee> employees = TestDataFactory.employees();
		when(employeeRepo.findAll()).thenReturn(employees);

		List<EmployeeResponse> actual = employeeService.getAllEmployees();
		verify(employeeRepo, times(1)).findAll();
		assertThat(actual)
				.isNotNull()
				.hasSameSizeAs(employees);
		assertThat(actual.getFirst().id()).isEqualTo(employees.getFirst().getId());
		assertThat(actual.getLast().id()).isEqualTo(employees.getLast().getId());
	}

	@Test
	@DisplayName("Update employee information")
	void updateEmployeeInfo() {
		Employee emp = TestDataFactory.newEmployee();
		EmployeeRequest request = TestDataFactory.employeeRequest();
		when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));

		employeeService.updateEmployeeInfo(1, request);

		verify(employeeRepo, times(1)).findById(1);
		verify(employeeRepo).save(empArgCaptor.capture());
		Employee actual = empArgCaptor.getValue();
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isEqualTo(emp.getId());
		assertThat(actual.getFirstName()).isEqualTo(request.firstName());
	}

	@Test
	@DisplayName("Promote employees to new rank")
	void updateEmployeesRanks() {
		List<Employee> employees = TestDataFactory.employees();
		when(employeeRepo.findById(1)).thenReturn(Optional.of(employees.getFirst()));
		when(employeeRepo.findById(2)).thenReturn(Optional.of(employees.getLast()));

		RankChangeRequest rankChangeRequest = TestDataFactory.promotionRequest();
		employeeService.updateEmployeesRanks(rankChangeRequest);

		verify(employeeRepo, times(2)).findById(any());
		verify(employeeRepo).saveAll(listArgCaptor.capture());
		List<Employee> actual = listArgCaptor.getValue();
		assertThat(actual.getFirst().getRank().name())
				.isEqualTo(rankChangeRequest.rankChanges().getFirst().rank());
	}

	@Test
	void transferEmployees() {
		List<Department> departments = TestDataFactory.departments();
		List<Employee> employees = TestDataFactory.employees();
		when(employeeRepo.findById(1)).thenReturn(Optional.of(employees.getFirst()));
		when(employeeRepo.findById(2)).thenReturn(Optional.of(employees.getLast()));
		when(deptService.getDepartment(1)).thenReturn(departments.getFirst());
		when(deptService.getDepartment(2)).thenReturn(departments.getLast());

		TransferRequest transferRequest = TestDataFactory.transferRequest();
		employeeService.transferEmployees(transferRequest);

		verify(deptService, times(2)).getDepartment(any());
		verify(employeeRepo, times(2)).findById(any());
		verify(employeeRepo).saveAll(listArgCaptor.capture());
		List<Employee> actual = listArgCaptor.getValue();
		assertThat(actual.getFirst().getDepartment().getId())
				.isEqualTo(transferRequest.transfers().getFirst().departmentId());
		assertThat(actual.getLast().getDepartment().getId())
				.isEqualTo(transferRequest.transfers().getLast().departmentId());
	}

	@Test
	void terminateEmployees() {
		List<Employee> employees = TestDataFactory.employees();
		when(employeeRepo.findById(1)).thenReturn(Optional.of(employees.getFirst()));
		when(employeeRepo.findById(2)).thenReturn(Optional.of(employees.getLast()));

		List<Integer> terminationRequest = TestDataFactory.terminationRequest();
		employeeService.terminateEmployees(terminationRequest);

		verify(employeeRepo, times(2)).findById(any());
		verify(employeeRepo).saveAll(listArgCaptor.capture());
		List<Employee> actual = listArgCaptor.getValue();
		assertThat(actual.getFirst().getStatus())
				.isEqualTo(EmployeeStatus.FIRED);
		assertThat(actual.getLast().getStatus())
				.isEqualTo(EmployeeStatus.FIRED);
	}
}
