package dev.aries.oneiroi.data;

import java.util.List;
import java.util.stream.IntStream;

import dev.aries.oneiroi.model.Department;
import dev.aries.oneiroi.model.Employee;
import dev.aries.oneiroi.model.Rank;
import dev.aries.oneiroi.repository.DepartmentRepository;
import dev.aries.oneiroi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Loader implements CommandLineRunner {
	private final DepartmentRepository departmentRepo;
	private final EmployeeRepository employeeRepo;
	private final Faker faker = new Faker();


	@Override
	public void run(String... args) throws Exception {
		loadData();
	}

	private void loadData() {
		if (departmentRepo.count() == 0 && employeeRepo.count() == 0) {
			Department department = new Department(
					faker.dcComics().title(),
					faker.famousLastWords().lastWords(),
					faker.number().randomDouble(2, 2500, 100000)
			);
			List<Employee> employees = IntStream.rangeClosed(1, 100)
					.mapToObj(e -> new Employee(
							faker.name().firstName(),
							faker.name().lastName(),
							faker.internet().emailAddress(),
							faker.phoneNumber().phoneNumber(),
							department,
							faker.options().option(Rank.class)
					)).toList();
			employeeRepo.saveAll(employees);
			log.info("Data loaded successfully");
		}
	}
}
