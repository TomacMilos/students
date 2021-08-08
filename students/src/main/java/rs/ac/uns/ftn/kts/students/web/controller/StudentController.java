package rs.ac.uns.ftn.kts.students.web.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.kts.students.model.Document;
import rs.ac.uns.ftn.kts.students.model.Enrollment;
import rs.ac.uns.ftn.kts.students.model.Exam;
import rs.ac.uns.ftn.kts.students.model.Payment;
import rs.ac.uns.ftn.kts.students.model.Student;
import rs.ac.uns.ftn.kts.students.model.User;
import rs.ac.uns.ftn.kts.students.service.*;
import rs.ac.uns.ftn.kts.students.web.dto.CourseDTO;
import rs.ac.uns.ftn.kts.students.web.dto.DocumentDTO;
import rs.ac.uns.ftn.kts.students.web.dto.EnrollmentDTO;
import rs.ac.uns.ftn.kts.students.web.dto.ExamDTO;
import rs.ac.uns.ftn.kts.students.web.dto.ExamPeriodDTO;
import rs.ac.uns.ftn.kts.students.web.dto.PaymentDTO;
import rs.ac.uns.ftn.kts.students.web.dto.StudentDTO;

@RestController
@RequestMapping(value = "api/students")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private ExamService examService;
	@Autowired
	private EnrollmentService enrollmentService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private UserService userService;

	public boolean l;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<StudentDTO>> getAllStudents() {
		List<Student> students = studentService.findAllByActive(true);
		// convert students to DTOs
		List<StudentDTO> studentsDTO = new ArrayList<>();
		for (Student s : students) {
			studentsDTO.add(new StudentDTO(s));
		}
		return new ResponseEntity<>(studentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<StudentDTO>> getStudentsForCourse(@PathVariable Long id) {
		List<Student> students = studentService.findAllByActive(true);
		// convert students to DTOs
		List<StudentDTO> studentiDTO = new ArrayList<>();
		List<Student> studentiKojiNisuPolozili = new ArrayList<>();

		for (Student s : students) {
			Set<Exam> exams = s.getExams();
			boolean izlazio = false;
			for (Exam e : exams) {
				if(e.getCourse().getId() == id) {
					izlazio = true;
					double points = e.getLabPoints() + e.getExamPoints();
					if (points < 51) {
						studentiKojiNisuPolozili.add(s);
					}
				}
			}
			if (!izlazio){
				studentiKojiNisuPolozili.add(s);
			}
		}
		studentiKojiNisuPolozili.forEach(student -> {
			l = false;
			student.getEnrollments().forEach(enrollment -> {
				if(enrollment.getEndDate().after(new Date()) && enrollment.getCourse().getId() == id){
					l = true;
				}
			});
			if(!l){
				studentiDTO.add(new StudentDTO(student));
			}
		});

		return new ResponseEntity<>(studentiDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
		Student student = studentService.findOne(id);
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO) {
		Student student = new Student();
		student.setCardNumber(studentDTO.getCardNumber());
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());

		student = studentService.save(student);
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO) {
		// a student must exist
		Student student = studentService.findOne(studentDTO.getId());
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		student.setCardNumber(studentDTO.getCardNumber());
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());

		student = studentService.save(student);
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		Student student = studentService.findOne(id);
		if (student != null) {

			student.setActive(false);

			studentService.save(student);

			for (Enrollment e : student.getEnrollments()) {
				if (e.getStudent().getId() == student.getId()) {
					student.remove(e);
					enrollmentService.save(e);
				}

			}
			List<User> users = userService.findAll();
			
			for (User user : users) {
				if (user.getStudent() != null) {
					if (user.getStudent().getId() == id) {
						userService.remove(user.getId());
					}
				}

			}
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/findCard", method = RequestMethod.GET)
	public ResponseEntity<StudentDTO> getStudentByCard(@RequestParam String cardNumber) {
		Student student = studentService.findByCard(cardNumber);
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.OK);
	}

	@RequestMapping(value = "/findLastName", method = RequestMethod.GET)
	public ResponseEntity<List<StudentDTO>> getStudentsByLastName(@RequestParam String lastName) {
		List<Student> students = studentService.findByLastName(lastName);
		// convert students to DTOs
		List<StudentDTO> studentsDTO = new ArrayList<>();
		for (Student s : students) {
			studentsDTO.add(new StudentDTO(s));
		}
		return new ResponseEntity<>(studentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{studentId}/courses", method = RequestMethod.GET)
	public ResponseEntity<List<EnrollmentDTO>> getStudentCourses(@PathVariable Long studentId) {
		Student student = studentService.findOne(studentId);
		Set<Enrollment> enrollments = student.getEnrollments();
		List<EnrollmentDTO> enrollmentsDTO = new ArrayList<>();
		for (Enrollment e : enrollments) {
			if(e.getEndDate().after(new Date())) {
				EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
				enrollmentDTO.setId(e.getId());
				enrollmentDTO.setStartDate(e.getStartDate());
				enrollmentDTO.setEndDate(e.getEndDate());
				if (e.getCourse() != null)
					enrollmentDTO.setCourse(new CourseDTO(e.getCourse()));
				// we leave student field empty

				enrollmentsDTO.add(enrollmentDTO);
			}
		}
		return new ResponseEntity<>(enrollmentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{studentId}/exams", method = RequestMethod.GET)
	public ResponseEntity<List<ExamDTO>> getStudentExams(@PathVariable Long studentId) {
		Student student = studentService.findOne(studentId);
		Set<Exam> exams = student.getExams();
		List<ExamDTO> examsDTO = new ArrayList<>();
		Date date = new Date();
		for (Exam e : exams) {
			if (e.getDate() != null) {
				
			if (e.getLabPoints() + e.getExamPoints() == 0 && e.getDate().before(date)) {
				ExamDTO examDTO = new ExamDTO();
				examDTO.setId(e.getId());
				examDTO.setExamPoints(e.getExamPoints());
				examDTO.setLabPoints(e.getLabPoints());
				examDTO.setDate(e.getDate());
				examDTO.setCourse(new CourseDTO(e.getCourse()));
				examDTO.setExamPeriod(new ExamPeriodDTO(e.getExamPeriod()));

				examsDTO.add(examDTO);
			}
			}
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{studentId}/nextexems", method = RequestMethod.GET)
	public ResponseEntity<List<ExamDTO>> getNextExams(@PathVariable Long studentId) {
		Student student = studentService.findOne(studentId);
		Set<Exam> exams = student.getExams();
		List<ExamDTO> examsDTO = new ArrayList<>();
		Date date = new Date();
		for (Exam e : exams) {
			if (e.getDate() != null) {
			if (e.getLabPoints() + e.getExamPoints() == 0 && e.getDate().after(date)) {
				ExamDTO examDTO = new ExamDTO();
				examDTO.setId(e.getId());
				examDTO.setExamPoints(e.getExamPoints());
				examDTO.setLabPoints(e.getLabPoints());
				examDTO.setDate(e.getDate());
				examDTO.setCourse(new CourseDTO(e.getCourse()));
				examDTO.setExamPeriod(new ExamPeriodDTO(e.getExamPeriod()));

				examsDTO.add(examDTO);
			}
			}
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);
	}


	@RequestMapping(value = "/{studentId}/examspass", method = RequestMethod.GET)
	public ResponseEntity<List<ExamDTO>> getStudentExamsPass(@PathVariable Long studentId) {
		Student student = studentService.findOne(studentId);
		Set<Exam> exams = student.getExams();
		List<ExamDTO> examsDTO = new ArrayList<>();
		Date date = new Date();
		for (Exam e : exams) {
			if (e.getLabPoints() + e.getExamPoints() > 0 && e.getDate().before(date)) {
				ExamDTO examDTO = new ExamDTO();
				examDTO.setId(e.getId());
				examDTO.setExamPoints(e.getExamPoints());
				examDTO.setLabPoints(e.getLabPoints());
				examDTO.setDate(e.getDate());
				if(e.getCourse() != null)
					examDTO.setCourse(new CourseDTO(e.getCourse()));
				examDTO.setExamPeriod(new ExamPeriodDTO(e.getExamPeriod()));

				examsDTO.add(examDTO);
			}
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{studentId}/documents", method = RequestMethod.GET)
	public ResponseEntity<List<DocumentDTO>> getStudentDocuments(@PathVariable Long studentId) {
		Student student = studentService.findOne(studentId);
		Set<Document> documents = student.getDocuments();
		List<DocumentDTO> documentsDTO = new ArrayList<>();
		for (Document d : documents) {
			DocumentDTO documentDTO = new DocumentDTO();
			documentDTO.setId(d.getId());
			documentDTO.setNaziv(d.getNaziv());
			// we leave student field empty

			documentsDTO.add(documentDTO);
		}
		return new ResponseEntity<>(documentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{studentId}/payments", method = RequestMethod.GET)
	public ResponseEntity<List<PaymentDTO>> getStudentPayments(@PathVariable Long studentId) {
		Student student = studentService.findOne(studentId);
		Set<Payment> payments = student.getPayments();
		List<PaymentDTO> paymentsDTO = new ArrayList<>();
		for (Payment p : payments) {
			PaymentDTO paymentDTO = new PaymentDTO();
			paymentDTO.setId(p.getId());
			paymentDTO.setSvrhaUplate(p.getSvrhaUplate());
			paymentDTO.setVrednostUplate(p.getVrednostUplate());
			paymentDTO.setDate(p.getDate());
			// we leave student field empty

			paymentsDTO.add(paymentDTO);
		}
		return new ResponseEntity<>(paymentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{studentId}/allSum", method = RequestMethod.GET)
	public ResponseEntity<Integer> getAllPaymentsSum(@PathVariable Long studentId) {
		Student student = studentService.findOne(studentId);
		int sum = 0;
		Set<Payment> payments = student.getPayments();

		for (Payment p : payments) {
			sum += p.getVrednostUplate();
		}
		return new ResponseEntity<>(sum, HttpStatus.OK);
	}
}
