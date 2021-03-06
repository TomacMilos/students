package rs.ac.uns.ftn.kts.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.kts.students.model.ExamPeriod;
import rs.ac.uns.ftn.kts.students.model.Teacher;

import java.util.List;

public interface ExamPeriodRepository extends JpaRepository<ExamPeriod, Long> {

    List<ExamPeriod> findAllByActive(boolean active);

}
