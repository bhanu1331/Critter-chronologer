package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setDate(dto.getDate());
        schedule.setActivities(dto.getActivities());

        Schedule saved = service.createSchedule(schedule, dto.getEmployeeIds(), dto.getPetIds());
        return convertSchedule(saved);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertList(service.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable Long petId) {
        return convertList(service.getScheduleForPet(petId));
    }

    @GetMapping("/employee/{empId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable Long empId) {
        return convertList(service.getScheduleForEmployee(empId));
    }

    @GetMapping("/customer/{custId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable Long custId) {
        return convertList(service.getScheduleForCustomer(custId));
    }

    private List<ScheduleDTO> convertList(List<Schedule> schedules) {
        List<ScheduleDTO> result = new ArrayList<>();
        for (Schedule schedule : schedules) {
            result.add(convertSchedule(schedule));
        }
        return result;
    }

    private ScheduleDTO convertSchedule(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setDate(schedule.getDate());
        dto.setActivities(schedule.getActivities());

        List<Long> employeeIds = new ArrayList<>();
        for (Employee e : schedule.getEmployees()) {
            employeeIds.add(e.getId());
        }
        dto.setEmployeeIds(employeeIds);

        List<Long> petIds = new ArrayList<>();
        for (Pet p : schedule.getPets()) {
            petIds.add(p.getId());
        }
        dto.setPetIds(petIds);

        return dto;
    }
}
