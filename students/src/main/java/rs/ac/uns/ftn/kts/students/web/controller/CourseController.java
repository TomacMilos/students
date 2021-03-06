package rs.ac.uns.ftn.kts.students.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.kts.students.model.Course;
import rs.ac.uns.ftn.kts.students.model.Enrollment;
import rs.ac.uns.ftn.kts.students.model.Exam;
import rs.ac.uns.ftn.kts.students.model.ExamPeriod;
import rs.ac.uns.ftn.kts.students.service.CourseService;
import rs.ac.uns.ftn.kts.students.service.EnrollmentService;
import rs.ac.uns.ftn.kts.students.service.ExamPeriodService;
import rs.ac.uns.ftn.kts.students.service.ExamService;
import rs.ac.uns.ftn.kts.students.web.dto.CourseDTO;
import rs.ac.uns.ftn.kts.students.web.dto.EnrollmentDTO;
import rs.ac.uns.ftn.kts.students.web.dto.ExamDTO;
import rs.ac.uns.ftn.kts.students.web.dto.ExamPeriodDTO;
import rs.ac.uns.ftn.kts.students.web.dto.StudentDTO;


@RestController
@RequestMapping(value="api/courses")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private ExamPeriodService  examPeriodService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CourseDTO>> getCourses() {
		List<Course> courses = courseService.findAllByActive(true);
		//convert courses to DTOs
		List<CourseDTO> coursesDTO = new ArrayList<>();
		for (Course s : courses) {
			coursesDTO.add(new CourseDTO(s));
		}
		return new ResponseEntity<>(coursesDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id){
		Course course = courseService.findOne(id);
		if(course == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new CourseDTO(course), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO){
		Course course = new Course();
		course.setName(courseDTO.getName());
	
		course = courseService.save(course);
		return new ResponseEntity<>(new CourseDTO(course), HttpStatus.CREATED);	
	}
	
	@RequestMapping(method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO){
		//a course must exist
		Course course = courseService.findOne(courseDTO.getId()); 
		if (course == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		course.setName(courseDTO.getName());
	
		course = courseService.save(course);
		return new ResponseEntity<>(new CourseDTO(course), HttpStatus.OK);	
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
		Course course = courseService.findOne(id);
		if (course != null){
			course.setActive(false);
			courseService.save(course);

			return new ResponseEntity<>(HttpStatus.OK);
		} else {		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{courseId}/students", method = RequestMethod.GET)
	public ResponseEntity<List<EnrollmentDTO>> getCourseStudents(
			@PathVariable Long courseId) {
		Course course = courseService.findOne(courseId);
		Set<Enrollment> enrollments = course.getEnrollments();
		List<EnrollmentDTO> enrollmentsDTO = new ArrayList<>();
		for (Enrollment e: enrollments) {
			if(e.getEndDate().after(new Date())) {
			EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
			enrollmentDTO.setId(e.getId());
			enrollmentDTO.setStartDate(e.getStartDate());
			enrollmentDTO.setEndDate(e.getEndDate());
			enrollmentDTO.setStudent(new StudentDTO(e.getStudent()));
			//we leave course field empty
			
			enrollmentsDTO.add(enrollmentDTO);
			}
		}
		
		return new ResponseEntity<>(enrollmentsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{courseId}/examscourse", method = RequestMethod.GET)
	public ResponseEntity<List<ExamDTO>> getStudentExams(
			@PathVariable Long courseId) {
		Course course = courseService.findOne(courseId);
		Set<Exam> exams = course.getExams();
		List<ExamDTO> examsDTO = new ArrayList<>();
		for (Exam e: exams) {
			ExamDTO examDTO = new ExamDTO();
			examDTO.setId(e.getId());
			examDTO.setExamPoints(e.getExamPoints());
			examDTO.setLabPoints(e.getLabPoints());
			examDTO.setDate(e.getDate());
			examDTO.setStudent(new StudentDTO(e.getStudent()));
			examDTO.setExamPeriod(new ExamPeriodDTO(e.getExamPeriod()));
		
			examsDTO.add(examDTO);
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);
	}
	@RequestMapping(value = "/{courseId}/examspasscourse", method = RequestMethod.GET)
	public ResponseEntity<List<ExamDTO>> getStudentExamsPass(
			@PathVariable Long courseId) {
		Course course = courseService.findOne(courseId);
		Set<Exam> exams = course.getExams();
		List<ExamDTO> examsDTO = new ArrayList<>();
		for (Exam e: exams) {
			if(e.getStudent()!=null && e.getExamPoints()==0 && e.getLabPoints()==0 && e.getDate().before(new Date())) {
			ExamDTO examDTO = new ExamDTO();
			examDTO.setId(e.getId());
			examDTO.setCourse(new CourseDTO(course));
			examDTO.setExamPoints(e.getExamPoints());
			examDTO.setLabPoints(e.getLabPoints());
			examDTO.setDate(e.getDate());
			examDTO.setStudent(new StudentDTO(e.getStudent()));
			examDTO.setExamPeriod(new ExamPeriodDTO(e.getExamPeriod()));
		
			examsDTO.add(examDTO);
			}
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{studentId}/{examPeriodId}", method = RequestMethod.GET)
	public ResponseEntity<List<CourseDTO>> getExamPeriodCourses(@PathVariable("studentId") Long studentId,
			@PathVariable("examPeriodId") Long examPeriodId) {
		
		List<Exam> allExams = examService.findAll();
		
		List<Enrollment> enrollments = enrollmentService.findAll();
		
		List<Course> coursesStudent = new ArrayList<>();
		
		for (Enrollment en : enrollments) {
			if (en.getStudent().getId() == studentId) {
				coursesStudent.add(en.getCourse());
			}
		}

		ExamPeriod examPeriod = examPeriodService.findOne(examPeriodId);
		
		Set<Exam> examPeriodExams = examPeriod.getExams();
		
		for (Exam exam : examPeriodExams) {
			if (exam.getStudent().getId() == studentId) {
				if (coursesStudent.contains(exam.getCourse())) {
					coursesStudent.remove(exam.getCourse());
				}
			}
		}
		
		for (Exam exam : allExams) {
			if (exam.getStudent().getId() == studentId) {
				if (coursesStudent.contains(exam.getCourse()) && (exam.getExamPoints() + exam.getLabPoints()) >= 51) {
					coursesStudent.remove(exam.getCourse());
				}
			}
		}
		
		List<CourseDTO> coursesDTO = new ArrayList<>();
		for (Course s : coursesStudent) {
			coursesDTO.add(new CourseDTO(s));
		}
		return new ResponseEntity<>(coursesDTO, HttpStatus.OK);
	}
	
}
