package rs.ac.uns.ftn.kts.students.web.dto;

import java.util.Date;

import rs.ac.uns.ftn.kts.students.model.Exam;

public class ExamDTO {
	private Long id;
    private Integer examPoints;
    private Integer labPoints;
    private Date date;
	private CourseDTO course;
	private StudentDTO student;
	private ExamPeriodDTO examPeriod;
	
	public ExamDTO() {
		
	}
	
	public ExamDTO(Exam exam) {
		id = exam.getId();
		examPoints = exam.getExamPoints();
		labPoints = exam.getLabPoints();
		date = exam.getDate();
		course = new CourseDTO(exam.getCourse());
		if (exam.getStudent() != null) {
			student = new StudentDTO(exam.getStudent());
		}
		examPeriod = new ExamPeriodDTO(exam.getExamPeriod());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getExamPoints() {
		return examPoints;
	}
	public void setExamPoints(Integer examPoints) {
		this.examPoints = examPoints;
	}
	public Integer getLabPoints() {
		return labPoints;
	}
	public void setLabPoints(Integer labPoints) {
		this.labPoints = labPoints;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public CourseDTO getCourse() {
		return course;
	}
	public void setCourse(CourseDTO course) {
		this.course = course;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}
	public ExamPeriodDTO getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(ExamPeriodDTO examPeriod) {
		this.examPeriod = examPeriod;
	}
}
