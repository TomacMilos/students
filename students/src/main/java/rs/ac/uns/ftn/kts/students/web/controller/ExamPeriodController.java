package rs.ac.uns.ftn.kts.students.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.kts.students.model.Exam;
import rs.ac.uns.ftn.kts.students.model.ExamPeriod;
import rs.ac.uns.ftn.kts.students.service.ExamPeriodService;
import rs.ac.uns.ftn.kts.students.service.ExamService;
import rs.ac.uns.ftn.kts.students.web.dto.CourseDTO;
import rs.ac.uns.ftn.kts.students.web.dto.ExamDTO;
import rs.ac.uns.ftn.kts.students.web.dto.ExamPeriodDTO;
import rs.ac.uns.ftn.kts.students.web.dto.StudentDTO;

@RestController
@RequestMapping(value="api/examPeriods")
public class ExamPeriodController {
	@Autowired
	private ExamPeriodService examPeriodService;
	
	@Autowired
	private ExamService examService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<ExamPeriodDTO>> getAllExamPeriods() {
		List<ExamPeriod> examPeriods = examPeriodService.findAll();
		//convert examPeriods to DTOs
		List<ExamPeriodDTO> examPeriodsDTO = new ArrayList<>();
		for (ExamPeriod s : examPeriods) {
			examPeriodsDTO.add(new ExamPeriodDTO(s));
		}
		return new ResponseEntity<>(examPeriodsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ExamPeriodDTO>> getExamPeriodsPage(Pageable page) {
		//page object holds data about pagination and sorting
		//the object is created based on the url parameters "page", "size" and "sort" 
		Page<ExamPeriod> examPeriods = examPeriodService.findAll(page);
		
		//convert examPeriods to DTOs
		List<ExamPeriodDTO> examPeriodsDTO = new ArrayList<>();
		for (ExamPeriod s : examPeriods) {
			examPeriodsDTO.add(new ExamPeriodDTO(s));
		}
		return new ResponseEntity<>(examPeriodsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ExamPeriodDTO> getExamPeriod(@PathVariable Long id){
		ExamPeriod examPeriod = examPeriodService.findOne(id);
		if(examPeriod == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new ExamPeriodDTO(examPeriod), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<ExamPeriodDTO> saveExamPeriod(@RequestBody ExamPeriodDTO examPeriodDTO){
		ExamPeriod examPeriod = new ExamPeriod();
		
		examPeriod.setName(examPeriodDTO.getName());
		examPeriod.setStartDate(examPeriodDTO.getStartDate());
		examPeriod.setEndDate(examPeriodDTO.getEndDate());
		
		examPeriod = examPeriodService.save(examPeriod);
		return new ResponseEntity<>(new ExamPeriodDTO(examPeriod), HttpStatus.CREATED);	
	}
	
	@RequestMapping(method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<ExamPeriodDTO> updateExamPeriod(@RequestBody ExamPeriodDTO examPeriodDTO){
		//a examPeriod must exist
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
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteExamPeriod(@PathVariable Long id){
		ExamPeriod examPeriod = examPeriodService.findOne(id);
		if (examPeriod != null){
			
			for (Exam e : examPeriod.getExams()) {
				if (e.getStudent().getId() == examPeriod.getId()) {
					examPeriod.remove(e);
					examService.save(e);
				}
			}
			
			examPeriodService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{examPeriodId}/exams", method = RequestMethod.GET)
	public ResponseEntity<List<ExamDTO>> getExamPeriodExams(
			@PathVariable Long examPeriodId) {
		ExamPeriod examPeriod = examPeriodService.findOne(examPeriodId);
		Set<Exam> exams = examPeriod.getExams();
		List<ExamDTO> examsDTO = new ArrayList<>();
		for (Exam e: exams) {
			ExamDTO examDTO = new ExamDTO();
			examDTO.setId(e.getId());
			examDTO.setExamPoints(e.getExamPoints());
			examDTO.setLabPoints(e.getLabPoints());
			examDTO.setDate(e.getDate());
			examDTO.setCourse(new CourseDTO(e.getCourse()));
			examDTO.setStudent(new StudentDTO(e.getStudent()));
		
			examsDTO.add(examDTO);
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);
	}
}
