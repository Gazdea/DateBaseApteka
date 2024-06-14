package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepositories extends JpaRepository<model.MedicalRecordBuilder, Integer> {
}
