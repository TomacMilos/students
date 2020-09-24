package rs.ac.uns.ftn.kts.students.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Exam {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    private Integer examPoints;
    
    private Integer labPoints;
    
    private Date date;
    
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Course course;
    
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Student student;
    
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private ExamPeriod examPeriod;
    
    public void remove(Exam exam, Course course) {
		for(Exam e : course.getExams()) {
			if (e.equals(exam)) {
				course.getExams().remove(e);
			}
		}
    }
    
    public void remove(Exam exam, Student student) {
		for(Exam e : student.getExams()) {
			if (e.equals(exam)) {
				student.getExams().remove(e);
			}
		}
    }
    
    public void remove(Exam exam, ExamPeriod examPeriod) {
		for(Exam e : examPeriod.getExams()) {
			if (e.equals(exam)) {
				examPeriod.getExams().remove(e);
			}
		}
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public ExamPeriod getExamPeriod() {
		return examPeriod;
	}

	public void setExamPeriod(ExamPeriod examPeriod) {
		this.examPeriod = examPeriod;
	}
}
