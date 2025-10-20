package crm.example.study.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    public Employee findEmployeeById(Long id);;

    public void removeEmployeeById(Long id);

    public Employee findByLogin(String login);
    // public List<Employee> getEmployeesByRole(Employee.Role role);
}
