package rs.ac.uns.ftn.kts.students.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ExamController {
	@Autowired
	ExamService examService;

	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseService;

	@Autowired
	ExamPeriodService examPeriodService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<ExamDTO>> getAllExams() {
		List<Exam> exams = examService.findAll();
		// convert students to DTOs
		List<ExamDTO> examsDTO = new ArrayList<>();
		for (Exam e : exams) {
			examsDTO.add(new ExamDTO(e));
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ExamDTO> getExam(@PathVariable Long id) {
		Exam exam = examService.findOne(id);
		if (exam == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ExamDTO(exam), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO) {
		// a new exam must have course and examPeriod defined
		if (examDTO.getCourse() == null || examDTO.getExamPeriod() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Course course = courseService.findOne(examDTO.getCourse().getId());
		ExamPeriod examPeriod = examPeriodService.findOne(examDTO.getExamPeriod().getId());
		if (course == null || examPeriod == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Exam exam = new Exam();
		Calendar cal = Calendar.getInstance();
		// remove next line if you're always using the current time.
		cal.setTime(examDTO.getDate());
		cal.add(Calendar.HOUR, -2);
		Date twoHourBack = cal.getTime();

		exam.setDate(twoHourBack);
		exam.setExamPoints(0);
		exam.setLabPoints(0);
		exam.setStudent(null);
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
	
	@RequestMapping(value = "/{studentId}/examRegistration", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ExamDTO> registerExam(@PathVariable Long studentId, @RequestBody ExamDTO examDTO) {
		// a new exam must have course and examPeriod defined
		if (examDTO.getCourse() == null || examDTO.getExamPeriod() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Course course = courseService.findOne(examDTO.getCourse().getId());
		ExamPeriod examPeriod = examPeriodService.findOne(examDTO.getExamPeriod().getId());
		Student student = studentService.findOne(studentId);
		if (course == null || examPeriod == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Exam exam = new Exam();
		exam.setDate(null);
		exam.setExamPoints(0);
		exam.setLabPoints(0);
		exam.setStudent(student);
		exam.setCourse(course);
		exam.setExamPeriod(examPeriod);

		exam = examService.save(exam);
		return new ResponseEntity<>(new ExamDTO(exam), HttpStatus.CREATED);
	}
	
}
