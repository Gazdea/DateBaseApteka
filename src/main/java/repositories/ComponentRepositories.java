package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepositories extends JpaRepository<model.ComponentBuilder, Integer> {
}
