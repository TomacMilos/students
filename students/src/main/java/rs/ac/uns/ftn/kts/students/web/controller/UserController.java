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
import rs.ac.uns.ftn.kts.students.util.PasswordBCrypt;
import rs.ac.uns.ftn.kts.students.web.dto.TeacherStudentID;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @PostMapping(value = "/registerStudent/{password}/{firstName}/{lastName}")
    public ResponseEntity<Void> registerStudent(
            @PathVariable("password") String password,
            @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {

        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setCardNumber("SF");

        student = studentService.save(student);
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        student.setCardNumber("SF-"+student.getId()+"-"+df.format(new Date()));
        student.setActive(true);
        student = studentService.save(student);

        User user = new User();
        user.setActive(true);
        user.setUsername(student.getCardNumber());
        user.setPassword(PasswordBCrypt.hashPassword(password));
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
        teacher.setActive(true);


        User user = userService.findByUsername(username);
        User newUser = new User();

        if(user == null) {
            teacher = teacherService.save(teacher);
            newUser.setUsername(username);
            newUser.setPassword(PasswordBCrypt.hashPassword(password));
            Authority auth = authorityService.findByName("NASTAVNIK");
            newUser.setAuthority(auth);
            newUser.setStudent(null);
            newUser.setTeacher(teacher);
            newUser.setActive(true);

            userService.save(newUser);
            System.out.println("REGISTER..........");
        }else {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Korisnik sa tim korisnickim imenom vec postoji");
        }
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @PostMapping(value = "/registerAdmin/{username}/{password}")
    public ResponseEntity<Void> registerAdmin(@PathVariable("username") String username,
                                              @PathVariable("password") String password) {

        User user = userService.findByUsername(username);
        User newUser = new User();
        if (user == null) {
            newUser.setUsername(username);
            newUser.setPassword(PasswordBCrypt.hashPassword(password));
            Authority auth = authorityService.findByName("ADMIN");
            newUser.setAuthority(auth);
            newUser.setStudent(null);
            newUser.setTeacher(null);
            userService.save(newUser);
            System.out.println("REGISTER..........");
        } else {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Korisnik sa tim korisnickim imenom vec postoji");
        }

        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<TeacherStudentID> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TeacherStudentID teacherStudentID = new TeacherStudentID();
        if (user.getTeacher() != null)
            teacherStudentID.setTeacherID(user.getTeacher().getId());
        if (user.getStudent() != null)
            teacherStudentID.setStudentID(user.getStudent().getId());

        return new ResponseEntity<>(teacherStudentID, HttpStatus.OK);
    }
}
