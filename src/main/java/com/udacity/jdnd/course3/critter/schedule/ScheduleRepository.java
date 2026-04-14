

package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPets_Id(Long petId);
    List<Schedule> findAllByEmployees_Id(Long employeeId);
    List<Schedule> findAllByPets_Owner_Id(Long ownerId);
}
