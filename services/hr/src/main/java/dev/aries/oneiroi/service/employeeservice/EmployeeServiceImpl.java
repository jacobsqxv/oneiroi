package dev.aries.oneiroi.service.employeeservice;

import java.util.ArrayList;
import java.util.List;

import dev.aries.oneiroi.constant.ExceptionConstant;
import dev.aries.oneiroi.dto.EmployeeRequest;
import dev.aries.oneiroi.dto.EmployeeResponse;
import dev.aries.oneiroi.dto.RankChangeRequest;
import dev.aries.oneiroi.dto.TransferRequest;
import dev.aries.oneiroi.model.Department;
import dev.aries.oneiroi.model.Employee;
import dev.aries.oneiroi.model.Rank;
import dev.aries.oneiroi.model.EmployeeStatus;
import dev.aries.oneiroi.repository.EmployeeRepository;
import dev.aries.oneiroi.service.departmentservice.DepartmentService;
import dev.aries.oneiroi.util.Util;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepo;
	private final DepartmentService departmentService;

	@Override
	public Employee getEmployee(Integer id) {
		log.info("Lookup for employee ID: '{}'", id);
		return employeeRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstant.NOT_FOUND.getMessage(), "Employee")));
	}

	@Override
	@Transactional
	public EmployeeResponse addNewEmployee(EmployeeRequest request) {
		Department department = departmentService.getDepartment(request.departmentId());
		Employee newEmployee = new Employee(
				request.firstName(),
				request.lastName(),
				request.email(),
				request.phoneNumber(),
				department,
				Rank.valueOf(request.rank().toUpperCase())
		);
		employeeRepo.save(newEmployee);
		log.info("New employee: '{}' added", newEmployee.fullName());
		return EmployeeResponse.basicResponse(newEmployee);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeResponse> getAllEmployees() {
		return employeeRepo.findAll().stream()
				.map(EmployeeResponse::listResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeeResponse getEmployeeResponse(Integer id) {
		Employee employee = getEmployee(id);
		return EmployeeResponse.fullResponse(employee);
	}

	@Override
	@Transactional
	public EmployeeResponse updateEmployeeInfo(Integer id, EmployeeRequest request) {
		Employee emp = getEmployee(id);
		EmployeeRequest update = EmployeeRequest.updateRequest(request);
		Util.updateField(emp::setFirstName, emp.getFirstName(), update.firstName());
		Util.updateField(emp::setLastName, emp.getLastName(), update.lastName());
		Util.updateField(emp::setEmail, emp.getEmail(), update.email());
		Util.updateField(emp::setPhoneNumber, emp.getPhoneNumber(), update.phoneNumber());
		employeeRepo.save(emp);
		log.info("Employee: '{}' updated", emp.getId());
		return EmployeeResponse.basicResponse(emp);
	}

	@Override
	@Transactional
	public List<EmployeeResponse> updateEmployeesRanks(RankChangeRequest request) {
		List<Employee> promoted = new ArrayList<>();
		for (RankChangeRequest.RankChange pr: request.rankChanges()) {
			Employee employee = getEmployee(pr.employeeId());
			Rank rank = Rank.valueOf(pr.rank().toUpperCase());
			employee.setRank(rank);
			promoted.add(employee);
			log.info("Employee: '{}' has been promoted to rank: '{}'", employee.fullName(), rank.name());
		}
		employeeRepo.saveAll(promoted);
		return promoted.stream()
				.map(EmployeeResponse::basicResponse)
				.toList();
	}

	@Override
	@Transactional
	public List<EmployeeResponse> transferEmployees(TransferRequest request) {
		List<Employee> transferred = new ArrayList<>();
		for (TransferRequest.Transfer tr : request.transfers()) {
			Employee employee = getEmployee(tr.employeeId());
			Department department = departmentService.getDepartment(tr.departmentId());
			employee.setDepartment(department);
			transferred.add(employee);
			log.info("Employee: '{}' has been transferred to Department: '{}'", employee.fullName(), department.getName());
		}
		employeeRepo.saveAll(transferred);
		return transferred.stream()
				.map(EmployeeResponse::basicResponse)
				.toList();
	}

	@Override
	@Transactional
	public Void terminateEmployees(List<Integer> employeeIds) {
		List<Employee> terminated = new ArrayList<>();
		for(Integer id: employeeIds) {
			Employee employee = getEmployee(id);
			employee.setStatus(EmployeeStatus.FIRED);
			terminated.add(employee);
			log.info("Employee: '{}' has been terminated", employee.fullName());
		}
		employeeRepo.saveAll(terminated);
		return null;
	}

}
