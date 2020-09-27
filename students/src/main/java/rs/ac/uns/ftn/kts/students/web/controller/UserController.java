package rs.ac.uns.ftn.kts.students.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.kts.students.model.User;
import rs.ac.uns.ftn.kts.students.service.UserService;
import rs.ac.uns.ftn.kts.students.web.dto.UserDTO;

@RestController
@RequestMapping(value = "api/user")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

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

}
