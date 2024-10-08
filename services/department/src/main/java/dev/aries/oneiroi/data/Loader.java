package dev.aries.oneiroi.data;

import java.util.List;
import java.util.stream.IntStream;

import dev.aries.oneiroi.department.model.Department;
import dev.aries.oneiroi.department.repository.DepartmentRepository;
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
	private final Faker faker = new Faker();


	@Override
	public void run(String... args) throws Exception {
		loadDepartments();
	}

	private void loadDepartments() {
		if (departmentRepo.count() == 0) {
			List<Department> departments = IntStream.rangeClosed(1, 20)
					.mapToObj(d -> new Department(
							faker.company().industry(),
							faker.famousLastWords().lastWords(),
							faker.number().randomDouble(2, 2500, 100000)
					)).toList();
			departmentRepo.saveAll(departments);
			log.info("Departments loaded successfully");
		}
	}
}
