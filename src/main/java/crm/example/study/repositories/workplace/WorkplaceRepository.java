package crm.example.study.repositories.workplace;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.example.study.model.workplaces.Workplace;


public interface WorkplaceRepository extends JpaRepository<Workplace, Long>{

    Optional<Workplace> findByName(String name);

}
