package rs.ac.uns.ftn.kts.students.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.kts.students.model.*;
import rs.ac.uns.ftn.kts.students.service.ExamPeriodService;
import rs.ac.uns.ftn.kts.students.service.ExamService;
import rs.ac.uns.ftn.kts.students.service.StudentService;
import rs.ac.uns.ftn.kts.students.web.dto.CourseDTO;
import rs.ac.uns.ftn.kts.students.web.dto.ExamDTO;
import rs.ac.uns.ftn.kts.students.web.dto.ExamPeriodDTO;
import rs.ac.uns.ftn.kts.students.web.dto.StudentDTO;

import java.util.*;

@RestController
@RequestMapping(value = "api/examPeriods")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ExamPeriodController {
    @Autowired
    private ExamPeriodService examPeriodService;

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ExamPeriodDTO>> getAllExamPeriods() {
        List<ExamPeriod> examPeriods = examPeriodService.findAllByActive(true);
        // convert examPeriods to DTOs
        List<ExamPeriodDTO> examPeriodsDTO = new ArrayList<>();
        for (ExamPeriod s : examPeriods) {
            examPeriodsDTO.add(new ExamPeriodDTO(s));
        }
        return new ResponseEntity<>(examPeriodsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/upcoming", method = RequestMethod.GET)
    public ResponseEntity<List<ExamPeriodDTO>> getUpcomingEP() {
        List<ExamPeriod> examPeriods = examPeriodService.findAllByActive(true);
        // convert examPeriods to DTOs
        List<ExamPeriodDTO> examPeriodsDTO = new ArrayList<>();
        for (ExamPeriod s : examPeriods) {
            if (s.getEndDate().after(new Date())) {
                examPeriodsDTO.add(new ExamPeriodDTO(s));
            }
        }
        return new ResponseEntity<>(examPeriodsDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ExamPeriodDTO>> getExamPeriodsPage(Pageable page) {
        // page object holds data about pagination and sorting
        // the object is created based on the url parameters "page", "size" and "sort"
        List<ExamPeriod> examPeriods = examPeriodService.findAllByActive(true);

        // convert examPeriods to DTOs
        List<ExamPeriodDTO> examPeriodsDTO = new ArrayList<>();
        for (ExamPeriod s : examPeriods) {
            examPeriodsDTO.add(new ExamPeriodDTO(s));
        }
        return new ResponseEntity<>(examPeriodsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ExamPeriodDTO> getExamPeriod(@PathVariable Long id) {
        ExamPeriod examPeriod = examPeriodService.findOne(id);
        if (examPeriod == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ExamPeriodDTO(examPeriod), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ExamPeriodDTO> saveExamPeriod(@RequestBody ExamPeriodDTO examPeriodDTO) {
        ExamPeriod examPeriod = new ExamPeriod();

        examPeriod.setName(examPeriodDTO.getName());
        examPeriod.setStartDate(examPeriodDTO.getStartDate());
        examPeriod.setEndDate(examPeriodDTO.getEndDate());

        examPeriod = examPeriodService.save(examPeriod);
        return new ResponseEntity<>(new ExamPeriodDTO(examPeriod), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<ExamPeriodDTO> updateExamPeriod(@RequestBody ExamPeriodDTO examPeriodDTO) {
        // a examPeriod must exist
        ExamPeriod examPeriod = examPeriodService.findOne(examPeriodDTO.getId());
        if (examPeriod == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        examPeriod.setName(examPeriodDTO.getName());
        examPeriod.setStartDate(examPeriodDTO.getStartDate());
        examPeriod.setEndDate(examPeriodDTO.getEndDate());

        examPeriod = examPeriodService.save(examPeriod);
        return new ResponseEntity<>(new ExamPeriodDTO(examPeriod), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteExamPeriod(@PathVariable Long id) {
        ExamPeriod examPeriod = examPeriodService.findOne(id);
        if (examPeriod != null) {
            examPeriod.setActive(false);
            examPeriodService.save(examPeriod);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{examPeriodId}/exams", method = RequestMethod.GET)
    public ResponseEntity<List<ExamDTO>> getExamPeriodExams(@PathVariable Long examPeriodId) {
        ExamPeriod examPeriod = examPeriodService.findOne(examPeriodId);
        Set<Exam> exams = examPeriod.getExams();
        List<ExamDTO> examsDTO = new ArrayList<>();
        for (Exam e : exams) {
            if (e.getStudent() != null) {
                ExamDTO examDTO = new ExamDTO();
                examDTO.setId(e.getId());
                examDTO.setExamPoints(e.getExamPoints());
                examDTO.setLabPoints(e.getLabPoints());
                examDTO.setDate(e.getDate());
                examDTO.setCourse(new CourseDTO(e.getCourse()));
                examDTO.setStudent(new StudentDTO(e.getStudent()));

                examsDTO.add(examDTO);
            }
        }
        return new ResponseEntity<>(examsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{examPeriodId}/exam/{studentID}", method = RequestMethod.GET)
    public ResponseEntity<Set<ExamDTO>> getExamPeriodExamsCheckIn(@PathVariable Long examPeriodId, @PathVariable Long studentID) {

        Student student = studentService.findOne(studentID);

        Set<Exam> sviIspitiStudenta = student.getExams();

        Set<Enrollment> enrollments = student.getEnrollments();

        ExamPeriod examPeriod = examPeriodService.findOne(examPeriodId);

        Set<Course> coursesStudent = new HashSet<>();

        enrollments.forEach(en -> {
            coursesStudent.add(en.getCourse());
        });

        sviIspitiStudenta.forEach(ispit -> {
            if (ispit.getLabPoints() + ispit.getExamPoints() > 50) {
                coursesStudent.remove(ispit.getCourse());
            }
        });

        examPeriod.getExams().forEach(ispit -> {
        	if(ispit.getStudent() != null) {
				sviIspitiStudenta.forEach(studentISpit -> {
					if (ispit.getStudent() == studentISpit.getStudent() && studentISpit.getCourse().equals(ispit.getCourse()) && studentISpit.getExamPeriod().equals(ispit.getExamPeriod())) {
						coursesStudent.remove(ispit.getCourse());
					}
				});
			}
        });

        Set<ExamDTO> examsDTO = new HashSet<>();
        coursesStudent.forEach(course -> {
            examPeriod.getExams().forEach(ep -> {
                if (ep.getStudent() == null && ep.getCourse().equals(course) && ep.getDate().after(new Date())) {
                    examsDTO.add(new ExamDTO(ep));
                }
            });
        });

        return new ResponseEntity<>(examsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/nextExamPeriods", method = RequestMethod.GET)
    public ResponseEntity<List<ExamPeriodDTO>> getNextExamPeriods() {
        List<ExamPeriod> examPeriods = examPeriodService.findAllByActive(true);
        Date date = new Date();
        // convert examPeriods to DTOs
        List<ExamPeriodDTO> examPeriodsDTO = new ArrayList<>();
        for (ExamPeriod s : examPeriods) {
            if (s.getEndDate().after(date)) {
                examPeriodsDTO.add(new ExamPeriodDTO(s));
            }
        }
        return new ResponseEntity<>(examPeriodsDTO, HttpStatus.OK);
    }

}
