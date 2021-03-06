package rs.ac.uns.ftn.kts.students.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.kts.students.model.Course;
import rs.ac.uns.ftn.kts.students.model.Teacher;
import rs.ac.uns.ftn.kts.students.model.User;
import rs.ac.uns.ftn.kts.students.service.CourseService;
import rs.ac.uns.ftn.kts.students.service.TeacherService;
import rs.ac.uns.ftn.kts.students.service.UserService;
import rs.ac.uns.ftn.kts.students.web.dto.CourseDTO;
import rs.ac.uns.ftn.kts.students.web.dto.TeacherDTO;

@RestController
@RequestMapping(value="api/teachers")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
		List<Teacher> teachers = teacherService.findAllByActive(true);
		//convert teachers to DTOs
		List<TeacherDTO> teachersDTO = new ArrayList<>();
		for (Teacher s : teachers) {
			teachersDTO.add(new TeacherDTO(s));
		}
		return new ResponseEntity<>(teachersDTO, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TeacherDTO>> getTeachersPage(Pageable page) {
		List<Teacher> teachers = teacherService.findAllByActive(true);


		//convert teachers to DTOs
		List<TeacherDTO> teachersDTO = new ArrayList<>();
		for (Teacher s : teachers) {
			teachersDTO.add(new TeacherDTO(s));
		}
		return new ResponseEntity<>(teachersDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id){
		Teacher teacher = teacherService.findOne(id);
		if(teacher == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new TeacherDTO(teacher), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<TeacherDTO> saveTeacher(@RequestBody TeacherDTO teacherDTO){
		Teacher teacher = new Teacher();
		teacher.setFirstName(teacherDTO.getFirstName());
		teacher.setLastName(teacherDTO.getLastName());
		teacher.setTeacherRank(teacherDTO.getTeacherRank());
		
		teacher = teacherService.save(teacher);
		return new ResponseEntity<>(new TeacherDTO(teacher), HttpStatus.CREATED);	
	}
	@RequestMapping(value="/{teacherId}/course/{courseId}", method=RequestMethod.PUT)
	public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long teacherId,@PathVariable Long courseId){
		Teacher teacher = teacherService.findOne(teacherId);
		Course course = courseService.findOne(courseId);
		if (teacher == null || course == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		teacher.getCourses().add(course);
		course.getTeachers().add(teacher);

		System.out.println(teacher.getCourses());
		teacher = teacherService.save(teacher);
		courseService.save(course);
		return new ResponseEntity<>(new TeacherDTO(teacher), HttpStatus.OK);	
	}

	@RequestMapping(value="/{teacherId}/remove/{courseId}", method=RequestMethod.PUT)
	public ResponseEntity<TeacherDTO> removeCourseTeacher(@PathVariable Long teacherId,@PathVariable Long courseId){
		Teacher teacher = teacherService.findOne(teacherId);
		Course course = courseService.findOne(courseId);
		if (teacher == null || course == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		teacher.getCourses().remove(course);
		course.getTeachers().remove(teacher);

		System.out.println(teacher.getCourses());
		teacher = teacherService.save(teacher);
		courseService.save(course);
		return new ResponseEntity<>(new TeacherDTO(teacher), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<TeacherDTO> addTeacherCourse(@RequestBody TeacherDTO teacherDTO){
		//a teacher must exist
		Teacher teacher = teacherService.findOne(teacherDTO.getId());
		if (teacher == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		teacher.setFirstName(teacherDTO.getFirstName());
		teacher.setLastName(teacherDTO.getLastName());
		teacher.setTeacherRank(teacherDTO.getTeacherRank());

		teacher = teacherService.save(teacher);
		return new ResponseEntity<>(new TeacherDTO(teacher), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteTeacher(@PathVariable Long id){
		Teacher teacher = teacherService.findOne(id);
		if (teacher != null){
			for (Course c : teacher.getCourses()) {
				c.remove(teacher);
			}

			teacher.setActive(false);
			teacherService.save(teacher);
			List<User> users = userService.findAll();
			
			for (User user : users) {
				if (user.getTeacher() != null) {
					if (user.getTeacher().getId() == id) {
						userService.remove(user.getId());
					}
				}

			}
			return new ResponseEntity<>(HttpStatus.OK);
		} else {		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{teacherId}/courses", method = RequestMethod.GET)
	public ResponseEntity<List<CourseDTO>> getTeacherCourses(
			@PathVariable Long teacherId) {
		Teacher teacher = teacherService.findOne(teacherId);
		Set<Course> courses = teacher.getCourses();
		List<CourseDTO> coursesDTO = new ArrayList<>();
		for (Course c: courses) {
			coursesDTO.add(new CourseDTO(c));
		}
		return new ResponseEntity<>(coursesDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{teacherId}/courses/add", method = RequestMethod.GET)
	public ResponseEntity<List<CourseDTO>> getTeacherCoursesAdd(
			@PathVariable Long teacherId) {
		Teacher teacher = teacherService.findOne(teacherId);
		List<Course> all = courseService.findAllByActive(true);
		all.removeAll(teacher.getCourses());
		List<CourseDTO> coursesDTO = new ArrayList<>();
		for (Course c: all) {
			coursesDTO.add(new CourseDTO(c));
		}
		return new ResponseEntity<>(coursesDTO, HttpStatus.OK);
	}
}
