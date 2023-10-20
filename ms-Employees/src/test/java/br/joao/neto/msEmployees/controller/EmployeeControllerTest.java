package br.joao.neto.msEmployees.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.joao.neto.msEmployees.model.Employee;
import br.joao.neto.msEmployees.model.DTO.EmployeeGetResponse;
import br.joao.neto.msEmployees.service.EmployeeService;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee employee = new Employee();

    @BeforeEach
    public void setUp()
    {
        String name = "Jhon Textor";
        UUID uuid = UUID.randomUUID();
        String cpf = "12345678915";
        Date date = new Date();
        employee.setName(name);
        employee.setId(uuid);
        employee.setCpf(cpf);
        employee.setRegistrationDate(date);
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Mockito.when(employeeService.findById(employee.getId())).thenReturn(new ResponseEntity<>(employee, HttpStatus.FOUND));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(employee.getCpf()));
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employeeTest = new Employee(employee.getName(), employee.getCpf());
        employee.setId(employee.getId());
        employee.setRegistrationDate(employee.getRegistrationDate());
        Mockito.when(employeeService.create(Mockito.any(Employee.class))).thenReturn(new ResponseEntity<>(employee, HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .content(asJsonString(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(employee.getCpf()));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employeeTest = new Employee();
        EmployeeGetResponse response = new EmployeeGetResponse();

        employeeTest.setId(employee.getId());
        employeeTest.setName("Tiquinho Soares");
        employeeTest.setRegistrationDate(employee.getRegistrationDate());
        employeeTest.setCpf(employee.getCpf());
        response.toModel(employeeTest);

        Mockito.when(employeeService.update(employeeTest.getId(), "Tiquinho Soares")).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}/{name}", employeeTest.getId(), "Tiquinho Soares")
            .content(asJsonString(employeeTest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employeeTest.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(employeeTest.getCpf()));
}

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
