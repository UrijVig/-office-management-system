package crm.example.repositories.workplace;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.models.entities.workplace.Workplace;


@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long>{

    Optional<Workplace> findByName(String name);

}
