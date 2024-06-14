package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationComponentRepositories extends JpaRepository<model.MedicationComponentBuilder, Integer> {
}
