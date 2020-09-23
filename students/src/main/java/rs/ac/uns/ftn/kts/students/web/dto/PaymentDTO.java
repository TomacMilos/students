package rs.ac.uns.ftn.kts.students.web.dto;

import java.util.Date;
import rs.ac.uns.ftn.kts.students.model.Payment;

public class PaymentDTO {

	private Long id;
	private String svrhaUplate;
	private String vrednostUplate;
	private Date date;
	private StudentDTO student;
	
	public PaymentDTO() {

	}
	
	public PaymentDTO(Payment payment) {
		id = payment.getId();
		svrhaUplate = payment.getSvrhaUplate();
		vrednostUplate = payment.getVrednostUplate();
		date = payment.getDate();
		student = new StudentDTO(payment.getStudent());
	}

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

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}
	
	
}
