package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    private final PetRepository petRepository;
    private final UserService service;

    public UserController(UserService service, PetRepository petRepository) {
        this.service = service;
        this.petRepository = petRepository;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setNotes(dto.getNotes());
        return convertCustomer(service.saveCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> result = new ArrayList<>();
        for (Customer c : service.getAllCustomers()) {
            result.add(convertCustomer(c));
        }
        return result;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable Long petId) {
        Customer customer = service.getOwnerByPet(petId);
        return customer == null ? null : convertCustomer(customer);
    }

    @PostMapping("/customer/{customerId}")
    public CustomerDTO addPetsToCustomer(@RequestBody List<Long> petIds,
                                         @PathVariable Long customerId) {
        service.addPetsToCustomer(customerId, petIds);
        return convertCustomer(service.getCustomerById(customerId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setSkills(dto.getSkills());
        employee.setDaysAvailable(dto.getDaysAvailable());

        Employee saved = service.saveEmployee(employee);
        return convertEmployee(saved);
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployee(@PathVariable Long id) {
        return convertEmployee(service.getEmployeeById(id));
    }

    @PutMapping("/employee/{id}")
    public void setAvailability(@RequestBody Set<DayOfWeek> days, @PathVariable Long id) {
        service.setAvailability(days, id);
    }

    @PostMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO req) {
        List<EmployeeDTO> result = new ArrayList<>();
        for (Employee e : service.findEmployeesForService(req.getDate().getDayOfWeek(), req.getSkills())) {
            result.add(convertEmployee(e));
        }
        return result;
    }

    private CustomerDTO convertCustomer(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setPhoneNumber(c.getPhoneNumber());
        dto.setNotes(c.getNotes());

        List<Long> petIds = new ArrayList<>();
        for (Pet p : petRepository.findByOwner_Id(c.getId())) {
            petIds.add(p.getId());
        }
        dto.setPetIds(petIds);
        return dto;
    }

    private EmployeeDTO convertEmployee(Employee e) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setSkills(e.getSkills());
        dto.setDaysAvailable(e.getDaysAvailable());
        return dto;
    }
}
