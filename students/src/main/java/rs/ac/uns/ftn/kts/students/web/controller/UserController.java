package rs.ac.uns.ftn.kts.students.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.kts.students.model.Authority;
import rs.ac.uns.ftn.kts.students.model.Student;
import rs.ac.uns.ftn.kts.students.model.Teacher;
import rs.ac.uns.ftn.kts.students.model.User;
import rs.ac.uns.ftn.kts.students.service.AuthorityService;
import rs.ac.uns.ftn.kts.students.service.StudentService;
import rs.ac.uns.ftn.kts.students.service.TeacherService;
import rs.ac.uns.ftn.kts.students.service.UserService;
import rs.ac.uns.ftn.kts.students.web.dto.UserDTO;

@RestController
@RequestMapping(value = "api/user")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private TeacherService teacherService;

	@GetMapping(value = "/login/{username}/{password}")
	public ResponseEntity<UserDTO> login(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		System.out.println("LOGIN..........");
		UserDTO jebeniDto = new UserDTO();
		User user = userService.findByUsernameAndPassword(username, password);
		if (user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		}
		if (user.getStudent() != null) {
			jebeniDto.setId(user.getId());
			jebeniDto.setPassword(user.getPassword());
			jebeniDto.setStudentid(user.getStudent().getId());
			jebeniDto.setUsername(user.getUsername());
			jebeniDto.setAuthority(user.getAuthority());

		} else if (user.getTeacher() != null) {
			jebeniDto.setId(user.getId());
			jebeniDto.setPassword(user.getPassword());
			jebeniDto.setTeacherid(user.getTeacher().getId());
			jebeniDto.setUsername(user.getUsername());
			jebeniDto.setAuthority(user.getAuthority());
		} else if(user.getStudent() == null && user.getTeacher() == null) {
			jebeniDto.setId(user.getId());
			jebeniDto.setPassword(user.getPassword());
			jebeniDto.setUsername(user.getUsername());
			jebeniDto.setAuthority(user.getAuthority());
		}

		return new ResponseEntity<UserDTO>(jebeniDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/registerStudent/{username}/{password}/{cardNumber}/{firstName}/{lastName}")
	public ResponseEntity<Void> registerStudent(@PathVariable("username") String username,
			@PathVariable("password") String password, @PathVariable("cardNumber") String cardNumber,
			@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
		
		Student student = new Student();
		student.setCardNumber(cardNumber);
		student.setFirstName(firstName);
		student.setLastName(lastName);

		student = studentService.save(student);
		
		User user = new User();
		
		user.setUsername(username);
		user.setPassword(password);
		Authority auth = authorityService.findByName("STUDENT");
		user.setAuthority(auth);
		user.setStudent(student);
		user.setTeacher(null);
		
		userService.save(user);
		System.out.println("REGISTER..........");
		return new ResponseEntity<Void>(HttpStatus.OK);

	}
	
	@PostMapping(value = "/registerTeacher/{username}/{password}/{firstName}/{lastName}/{teacherRank}")
	public ResponseEntity<Void> registerTeacher(@PathVariable("username") String username,
			@PathVariable("password") String password, @PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName, @PathVariable("teacherRank") String teacherRank){
		
		Teacher teacher = new Teacher();
		teacher.setFirstName(firstName);
		teacher.setLastName(lastName);
		teacher.setTeacherRank(teacherRank);
		teacher.setCourses(null);

		teacher = teacherService.save(teacher);
		
		User user = new User();
		
		user.setUsername(username);
		user.setPassword(password);
		Authority auth = authorityService.findByName("NASTAVNIK");
		user.setAuthority(auth);
		user.setStudent(null);
		user.setTeacher(teacher);
		
		userService.save(user);
		System.out.println("REGISTER..........");
		return new ResponseEntity<Void>(HttpStatus.OK);

	}
	
	@PostMapping(value = "/registerAdmin/{username}/{password}")
	public ResponseEntity<Void> registerAdmin(@PathVariable("username") String username,
			@PathVariable("password") String password){
		
		User user = new User();
		
		user.setUsername(username);
		user.setPassword(password);
		Authority auth = authorityService.findByName("ADMIN");
		user.setAuthority(auth);
		user.setStudent(null);
		user.setTeacher(null);
		
		userService.save(user);
		System.out.println("REGISTER..........");
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

}
