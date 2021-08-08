package rs.ac.uns.ftn.kts.students.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.kts.students.model.Authority;
import rs.ac.uns.ftn.kts.students.model.Student;
import rs.ac.uns.ftn.kts.students.model.Teacher;
import rs.ac.uns.ftn.kts.students.model.User;
import rs.ac.uns.ftn.kts.students.service.AuthorityService;
import rs.ac.uns.ftn.kts.students.service.StudentService;
import rs.ac.uns.ftn.kts.students.service.TeacherService;
import rs.ac.uns.ftn.kts.students.service.UserService;
import rs.ac.uns.ftn.kts.students.web.dto.StudentDTO;
import rs.ac.uns.ftn.kts.students.web.dto.TeacherStudentID;
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

    @PostMapping(value = "/registerStudent/{username}/{password}/{cardNumber}/{firstName}/{lastName}")
    public ResponseEntity<Void> registerStudent(@PathVariable("username") String username,
                                                @PathVariable("password") String password, @PathVariable("cardNumber") String cardNumber,
                                                @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {

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
                                                @PathVariable("lastName") String lastName, @PathVariable("teacherRank") String teacherRank) {

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
                                              @PathVariable("password") String password) {

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
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<TeacherStudentID> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TeacherStudentID teacherStudentID = new TeacherStudentID();
        if(user.getTeacher() !=null)
            teacherStudentID.setTeacherID(user.getTeacher().getId());
        if(user.getStudent() != null)
            teacherStudentID.setStudentID(user.getStudent().getId());

        return new ResponseEntity<>(teacherStudentID, HttpStatus.OK);
    }
}
