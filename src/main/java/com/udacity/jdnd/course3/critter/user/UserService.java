package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final CustomerRepository customerRepo;
    private final EmployeeRepository employeeRepo;
    private final PetRepository petRepo;

    public UserService(CustomerRepository customerRepo,
                       EmployeeRepository employeeRepo,
                       PetRepository petRepo) {
        this.customerRepo = customerRepo;
        this.employeeRepo = employeeRepo;
        this.petRepo = petRepo;
    }

    // ---------- CUSTOMER ----------

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public Customer getOwnerByPet(Long petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
        return pet.getOwner();
    }

    public void addPetsToCustomer(Long customerId, List<Long> petIds) {
        Customer customer = getCustomerById(customerId);
        List<Pet> pets = petRepo.findAllById(petIds);

        for (Pet pet : pets) {
            pet.setOwner(customer);
        }
        petRepo.saveAll(pets);
    }

    // ---------- EMPLOYEE ----------

    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    }

    public void setAvailability(Set<DayOfWeek> days, Long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(EnumSet.copyOf(days));
        employeeRepo.save(employee);
    }

    public List<Employee> findEmployeesForService(DayOfWeek day, Set<EmployeeSkill> skills) {
        List<Employee> available = employeeRepo.findAllByDaysAvailable(day);
        List<Employee> qualified = new ArrayList<>();

        for (Employee employee : available) {
            if (employee.getSkills() != null && employee.getSkills().containsAll(skills)) {
                qualified.add(employee);
            }
        }
        return qualified;
    }
}
