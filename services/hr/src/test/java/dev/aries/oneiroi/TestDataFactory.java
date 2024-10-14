package dev.aries.oneiroi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import dev.aries.oneiroi.dto.DepartmentRequest;
import dev.aries.oneiroi.dto.DepartmentResponse;
import dev.aries.oneiroi.dto.EmployeeRequest;
import dev.aries.oneiroi.dto.RankChangeRequest;
import dev.aries.oneiroi.dto.TransferRequest;
import dev.aries.oneiroi.model.Department;
import dev.aries.oneiroi.model.DepartmentStatus;
import dev.aries.oneiroi.model.Employee;
import dev.aries.oneiroi.model.EmployeeStatus;
import dev.aries.oneiroi.model.Rank;

public class TestDataFactory {

	private static final Department.DepartmentBuilder DEPT_BUILDER = Department.builder()
			.name("New Department")
			.description("New description")
			.budget(10000.00)
			.employees(new HashSet<>())
			.status(DepartmentStatus.ACTIVE)
			.createdAt(LocalDateTime.now());

	private static final Employee.EmployeeBuilder EMPLOYEE_BUILDER = Employee.builder()
			.firstName("Jane")
			.lastName("Dee")
			.email("jane.dee@example.com")
			.phoneNumber("0500000001")
			.department(DEPT_BUILDER.id(2).build())
			.rank(Rank.EXPERT)
			.status(EmployeeStatus.ACTIVE)
			.createdAt(LocalDateTime.now());

	public static DepartmentRequest deptRequest() {
		return new DepartmentRequest(
				"Test Department",
				"Test description",
				20000.00
		);
	}

	public static EmployeeRequest employeeRequest() {
		return new EmployeeRequest(
				"John",
				"Doe",
				"john.doe@example.com",
				"0500000000",
				1,
				"JUNIOR"
		);
	}

	public static Employee newEmployee() {
		return EMPLOYEE_BUILDER.id(1).build();
	}

	public static Department newDept() {
		return DEPT_BUILDER.id(1).build();
	}

	public static List<Department> departments() {
		Department dept1 = DEPT_BUILDER.id(1).build();
		Department dept2 = DEPT_BUILDER.id(2).build();
		return List.of(dept1, dept2);
	}

	public static List<Employee> employees() {
		Department secondDept = DEPT_BUILDER.id(2).build();
		Employee employee1 = EMPLOYEE_BUILDER.id(1).rank(Rank.JUNIOR).build();
		Employee employee2 = EMPLOYEE_BUILDER.id(2).department(secondDept).build();
		return List.of(employee1, employee2);
	}

	public static RankChangeRequest promotionRequest() {
		RankChangeRequest.RankChange firstRankChange = new RankChangeRequest.RankChange(1,"EXPERT");
		RankChangeRequest.RankChange secondRankChange = new RankChangeRequest.RankChange(2,"SENIOR");
		return new RankChangeRequest(List.of(firstRankChange, secondRankChange));
	}

	public static TransferRequest transferRequest() {
		TransferRequest.Transfer firstTransfer = new TransferRequest.Transfer(1, 2);
		TransferRequest.Transfer secondTransfer = new TransferRequest.Transfer(2, 1);
		return new TransferRequest(List.of(firstTransfer, secondTransfer));
	}

	public static List<Integer> terminationRequest() {
		List<Integer> employeeIds = new ArrayList<>();
		employeeIds.add(1);
		employeeIds.add(2);
		return employeeIds;
	}

	public static DepartmentResponse newDeptResponse(String name) {
		return new DepartmentResponse(
				1, name, "Description", 20.0,
				new HashSet<>(),
				DepartmentStatus.ACTIVE.toString(),LocalDateTime.now());
	}
}
