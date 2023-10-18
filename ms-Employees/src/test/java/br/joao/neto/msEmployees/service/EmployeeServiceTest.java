package br.joao.neto.msEmployees.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.joao.neto.msEmployees.model.Employee;
import br.joao.neto.msEmployees.repo.EmployeeRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employeeService = new EmployeeService(employeeRepository);
        employee = new Employee("Jhon Textor", "12345678901");
    }

    @Test
    public void testCreateEmployee() {
        given(employeeRepository.save(employee)).willReturn(employee);

        Employee createdEmployee = employeeService.create(employee);

        assertThat(createdEmployee).isNotNull();
        assertThat(createdEmployee.getId()).isEqualTo(employee.getId());
        assertThat(createdEmployee.getCpf()).isEqualTo(employee.getCpf());
        assertThat(createdEmployee.getName()).isEqualTo(employee.getName());
    }

    @Test
    public void testFindEmployeeById() {
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.findById(employee.getId());

        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getId()).isEqualTo(employee.getId());
        assertThat(foundEmployee.getCpf()).isEqualTo(employee.getCpf());
        assertThat(foundEmployee.getName()).isEqualTo(employee.getName());
    }

    @Test
    public void testFindEmployeeByCpf() {
        given(employeeRepository.findByCPF(employee.getCpf())).willReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.findByCpf(employee.getCpf());

        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getId()).isEqualTo(employee.getId());
        assertThat(foundEmployee.getCpf()).isEqualTo(employee.getCpf());
        assertThat(foundEmployee.getName()).isEqualTo(employee.getName());
    }

    @Test
    public void testFindEmployeeByName() {
        given(employeeRepository.findByNameContainsAllIgnoringCase(employee.getName())).willReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.findByName(employee.getName());

        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getId()).isEqualTo(employee.getId());
        assertThat(foundEmployee.getCpf()).isEqualTo(employee.getCpf());
        assertThat(foundEmployee.getName()).isEqualTo(employee.getName());
    }

    @Test
    public void testFindEmployeeByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        given(employeeRepository.findById(nonExistentId)).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> employeeService.findById(nonExistentId));
    }

    @Test
    public void testFindEmployeeByCpfNotFound() {
        String nonExistentCpf = "98765432109";
        given(employeeRepository.findByCPF(nonExistentCpf)).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> employeeService.findByCpf(nonExistentCpf));
    }

    @Test
    public void testFindEmployeeByNameNotFound() {
        String nonExistentName = "Nonexistent Name";
        given(employeeRepository.findByNameContainsAllIgnoringCase(nonExistentName)).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> employeeService.findByName(nonExistentName));
    }
}




