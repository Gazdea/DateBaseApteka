package repositories;

import model.MedicationBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepositories extends JpaRepository<MedicationBuilder, Integer> {
}
