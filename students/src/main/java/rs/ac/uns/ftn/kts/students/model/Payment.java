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
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String svrhaUplate;
	
	private String vrednostUplate;
	
	private Date date;
	
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Student student;
    
//    public void remove(Student student, Payment payment) {
//		for(Payment p : student.getPayments()) {
//			if (p.equals(payment)) {
//				student.getPayments().remove(p);
//			}
//		}
//    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSvrhaUplate() {
		return svrhaUplate;
	}

	public void setSvrhaUplate(String svrhaUplate) {
		this.svrhaUplate = svrhaUplate;
	}

	public String getVrednostUplate() {
		return vrednostUplate;
	}

	public void setVrednostUplate(String vrednostUplate) {
		this.vrednostUplate = vrednostUplate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
    
    

}
