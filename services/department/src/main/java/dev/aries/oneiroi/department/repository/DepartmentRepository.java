package dev.aries.oneiroi.department.repository;

import java.util.Optional;

import dev.aries.oneiroi.department.model.Department;
import lombok.NonNull;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	@EntityGraph(value = "Department.employees", type = EntityGraphType.FETCH)
	Optional<Department> findByName(String name);

	@NonNull
	@EntityGraph(value = "Department.employees", type = EntityGraphType.FETCH)
	Optional<Department> findById(@NonNull Integer id);
}
