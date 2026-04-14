package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final PetRepository petRepo;
    private final EmployeeRepository employeeRepo;

    public ScheduleService(ScheduleRepository scheduleRepo,
                           PetRepository petRepo,
                           EmployeeRepository employeeRepo) {
        this.scheduleRepo = scheduleRepo;
        this.petRepo = petRepo;
        this.employeeRepo = employeeRepo;
    }

    public Schedule createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {

        Set<Employee> employees = new HashSet<>(employeeRepo.findAllById(employeeIds));
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("No employees found for schedule");
        }

        Set<Pet> pets = new HashSet<>(petRepo.findAllById(petIds));
        if (pets.isEmpty()) {
            throw new IllegalArgumentException("No pets found for schedule");
        }

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepo.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepo.findAll();
    }

    public List<Schedule> getScheduleForPet(Long petId) {
        return scheduleRepo.findAllByPets_Id(petId);
    }

    public List<Schedule> getScheduleForEmployee(Long empId) {
        return scheduleRepo.findAllByEmployees_Id(empId);
    }

    public List<Schedule> getScheduleForCustomer(Long custId) {
        return scheduleRepo.findAllByPets_Owner_Id(custId);
    }
}
