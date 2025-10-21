package crm.example.study.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

    Role findByRole(String role);
    
}
