package br.joao.neto.msEmployees.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import br.joao.neto.msEmployees.model.Employee;
import br.joao.neto.msEmployees.model.DTO.EmployeeGetResponse;
import br.joao.neto.msEmployees.repo.EmployeeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = new Employee("Jhon Textor", "12345678901");
        UUID id = UUID.randomUUID();
        employee.setId(id);
    }

    @Test
    public void testCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee savedEmployee = invocation.getArgument(0);
            savedEmployee.setId(UUID.randomUUID());
            return savedEmployee;
        });

        ResponseEntity<Employee> createdEmployeeResponse = employeeService.create(employee);

        Assertions.assertThat(createdEmployeeResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdEmployeeResponse.getBody()).isEqualTo(employee);
    }

    @Test
    public void testFindEmployeeById() {
        UUID id = employee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> foundEmployeeResponse = employeeService.findById(id);

        Assertions.assertThat(foundEmployeeResponse.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(foundEmployeeResponse.getBody()).isEqualTo(employee);
    }

    @Test
    public void testFindEmployeeByCpf() {
        String cpf = employee.getCpf();
        when(employeeRepository.findBycpf(cpf)).thenReturn(Optional.of(employee));

        ResponseEntity<EmployeeGetResponse> foundEmployeeResponse = employeeService.findByCpf(cpf);

        Assertions.assertThat(foundEmployeeResponse.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(foundEmployeeResponse.getBody().getCpf()).isEqualTo(employee.getCpf());
    }


    @Test
    public void testFindEmployeeByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        given(employeeRepository.findById(nonExistentId)).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> employeeService.findById(nonExistentId));
    }

    @Test
    public void testFindEmployeeByCpfNotFound() {
        String cpf = "12345678902";
        when(employeeRepository.findBycpf(cpf)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            employeeService.findByCpf(cpf);
        });
    }

    @Test
    public void testFindEmployeeByNameNotFound() {
        String name = "John";
        when(employeeRepository.findByNameIgnoreCase(name)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            employeeService.findByName(name);
        });
    }
}




