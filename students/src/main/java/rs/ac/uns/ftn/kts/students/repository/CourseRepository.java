package rs.ac.uns.ftn.kts.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.kts.students.model.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByActive(boolean active);

}
