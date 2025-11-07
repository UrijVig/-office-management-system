package crm.example.study.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import crm.example.study.exceptions.InvalidUsernameException;
import crm.example.study.model.employees.Employee;
import crm.example.study.model.employees.Role;
import crm.example.study.model.employees.DTO.EmployeeDTO;
import crm.example.study.repositories.employee.EmployeeRepository;
import crm.example.study.repositories.employee.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO;
    private Role role;
    
    
    @BeforeEach
    private void setEmployee(){
        this.role = new Role(1L, "ADMIN");
        this.employeeDTO = new EmployeeDTO(null, "username", "oldPass", "user"
        , "sur", "ADMIN");
    }

    @Test
    void testSaveEmployee() throws InvalidUsernameException {
        when(employeeRepository.findByUsername("username")).thenReturn(null);
        when(roleRepository.findByRole("ADMIN")).thenReturn(role);
        when(passwordEncoder.encode("oldPass")).thenReturn("endcodePass");

        employeeService.saveEmployee(employeeDTO);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());

        Employee saved = captor.getValue();

        assertThat(saved.getId()).isNull();
        assertThat(saved.getUsername()).isEqualTo("username");
        assertThat(saved.getName()).isEqualTo("user");
        assertThat(saved.getSurname()).isEqualTo("sur");
        assertThat(saved.getPassword()).isEqualTo("endcodePass");
        assertThat(saved.getRole()).isEqualTo(role);
        assertThat(saved.isActive()).isTrue()
                .isEqualTo(saved.isAccountNonExpired())
                .isEqualTo(saved.isAccountNonLocked())
                .isEqualTo(saved.isCredentialsNonExpired())
                .isEqualTo(saved.isEnabled());

        verify(passwordEncoder).encode("oldPass");
        verify(roleRepository).findByRole("ADMIN");
    }

    @Test
    void testUpdateEmployee() {

    }
    
    @Test
    void testChangeEmployeePassword() {
        
    }

    @Test
    void testResetEmployeePassword() {

    }

    @Test
    void testSetEmployeeActive() {

    }
}
