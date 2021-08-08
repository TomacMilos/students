package rs.ac.uns.ftn.kts.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.kts.students.model.Course;
import rs.ac.uns.ftn.kts.students.model.Teacher;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

        List<Teacher> findAllByActive(boolean active);

}
