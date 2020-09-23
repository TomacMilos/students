package rs.ac.uns.ftn.kts.students.web.dto;

import rs.ac.uns.ftn.kts.students.model.Document;

public class DocumentDTO {

	private Long id;
	private String naziv;
	private StudentDTO student;
	
	public DocumentDTO() {

	}
	
	public DocumentDTO(Document document) {
		id = document.getId();
		naziv = document.getNaziv();
		student = new StudentDTO(document.getStudent());
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}	
	
}
