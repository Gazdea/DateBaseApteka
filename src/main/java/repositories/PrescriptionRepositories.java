package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepositories extends JpaRepository<model.PrescriptionBuilder, Integer> {
}
