package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where :count = " +
            "(select count(distinct skill) from Employee e2 " +
            "inner join e2.skills skill where skill in :skills and e = e2) " +
            "and :dayOfWeek member of e.daysAvailable")

    List<Employee> findAvailable(Set<EmployeeSkill> skills, Long count, DayOfWeek dayOfWeek);
}
