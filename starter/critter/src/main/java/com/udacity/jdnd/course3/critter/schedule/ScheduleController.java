package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

   @Autowired
    CustomerService customerService;

   @Autowired
    PetService petService;


    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOTOSchedule(scheduleDTO);
        return convertScheduleToScheduleDTO(scheduleService.save(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        schedules.forEach(schedule -> scheduleDTOList.add(convertScheduleToScheduleDTO(schedule)));
        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        schedules.forEach(schedule -> scheduleDTOList.add(convertScheduleToScheduleDTO(schedule)));
        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        schedules.forEach(schedule -> scheduleDTOList.add(convertScheduleToScheduleDTO(schedule)));
        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getById(customerId);
        LinkedHashSet<Schedule> schedules = new LinkedHashSet<>();

        for (Pet pet : customer.getPets()) {
            schedules.addAll(scheduleService.getScheduleForPet(pet.getId()));
        }

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        schedules.forEach(schedule -> scheduleDTOList.add(convertScheduleToScheduleDTO(schedule)));

        return scheduleDTOList;
    }


    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getEmployees() != null) {
            List<Long> Ids = new ArrayList<>();
            for (Employee e : schedule.getEmployees()) {
                Ids.add(e.getId());
            }
            scheduleDTO.setEmployeeIds(Ids);
        }

        if (schedule.getPets() != null) {
            List<Long> Ids = new ArrayList<>();
            for (Pet p : schedule.getPets()) {
                Ids.add(p.getId());
            }
            scheduleDTO.setPetIds(Ids);
        }

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOTOSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getEmployeeIds() != null) {
            List<Employee> employees = new ArrayList<>();
            for (Long id : scheduleDTO.getEmployeeIds()) {
                employees.add(employeeService.findById(id));
            }
            schedule.setEmployees(employees);
        }

        if (scheduleDTO.getPetIds() != null) {
            List<Pet> pets = new ArrayList<>();
            for (Long id : scheduleDTO.getPetIds()) {
                pets.add(petService.findPetById(id));
            }
            schedule.setPets(pets);
        }
        return schedule;
    }
}
