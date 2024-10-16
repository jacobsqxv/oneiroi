package dev.aries.oneiroi.service.employeeservice;

import java.util.List;

import dev.aries.oneiroi.dto.EmployeeRequest;
import dev.aries.oneiroi.dto.EmployeeResponse;
import dev.aries.oneiroi.dto.GenericResponse;
import dev.aries.oneiroi.dto.RankChangeRequest;
import dev.aries.oneiroi.dto.TransferRequest;
import dev.aries.oneiroi.model.Employee;

public interface EmployeeService {

	Employee getEmployee(Integer id);

	EmployeeResponse addNewEmployee(EmployeeRequest request);

	List<EmployeeResponse> getAllEmployees();

	EmployeeResponse getEmployeeResponse(Integer id);

	EmployeeResponse updateEmployeeInfo(Integer id, EmployeeRequest request);

	List<EmployeeResponse> updateEmployeesRanks(RankChangeRequest request);

	List<EmployeeResponse> transferEmployees(TransferRequest request);

	GenericResponse terminateEmployees(List<Integer> employeeIds);
}
