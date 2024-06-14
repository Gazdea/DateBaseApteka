package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepositories extends JpaRepository<model.PatientBuilder, Integer> {
}
