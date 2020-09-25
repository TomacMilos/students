package rs.ac.uns.ftn.kts.students.web.controller;

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
import rs.ac.uns.ftn.kts.students.model.Exam;
import rs.ac.uns.ftn.kts.students.model.ExamPeriod;
import rs.ac.uns.ftn.kts.students.model.Student;
import rs.ac.uns.ftn.kts.students.service.CourseService;
import rs.ac.uns.ftn.kts.students.service.ExamPeriodService;
import rs.ac.uns.ftn.kts.students.service.ExamService;
import rs.ac.uns.ftn.kts.students.service.StudentService;
import rs.ac.uns.ftn.kts.students.web.dto.ExamDTO;

@RestController
@RequestMapping(value = "api/exams")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
public class ExamController {
	@Autowired
	ExamService examService;

	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseService;

	@Autowired
	ExamPeriodService examPeriodService;
	
	

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO) {
		// a new exam must have student, course and examPeriod defined
		if (examDTO.getStudent() == null || examDTO.getCourse() == null
				|| examDTO.getExamPeriod() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Student student = studentService.findOne(examDTO.getStudent().getId());
		Course course = courseService.findOne(examDTO.getCourse().getId());
		ExamPeriod examPeriod = examPeriodService.findOne(examDTO
				.getExamPeriod().getId());
		if (student == null || course == null || examPeriod == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Exam exam = new Exam();
		exam.setDate(examDTO.getDate());
		exam.setExamPoints(examDTO.getExamPoints());
		exam.setLabPoints(examDTO.getLabPoints());
		exam.setStudent(student);
		exam.setCourse(course);
		exam.setExamPeriod(examPeriod);

		exam = examService.save(exam);
		return new ResponseEntity<>(new ExamDTO(exam), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<ExamDTO> updateExam(@RequestBody ExamDTO examDTO) {
		// an exam must exist
		Exam exam = examService.findOne(examDTO.getId());
		if (exam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// we allow changing date and points for an exam only
		exam.setDate(examDTO.getDate());
		exam.setExamPoints(examDTO.getExamPoints());
		exam.setLabPoints(examDTO.getLabPoints());

		exam = examService.save(exam);
		return new ResponseEntity<>(new ExamDTO(exam), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
		Exam exam = examService.findOne(id);
		if (exam != null) {
			examService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
