package rs.ac.uns.ftn.kts.students.web.dto;

import rs.ac.uns.ftn.kts.students.model.Teacher;

public class TeacherDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String teacherRank;
	
	public TeacherDTO() {
		
	}

	public TeacherDTO(Teacher teacher) {
		this(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getTeacherRank());
	}

	public TeacherDTO(Long id, String firstName, String lastName, String teacherRank) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.teacherRank = teacherRank;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getTeacherRank() {
		return teacherRank;
	}

	public void setTeacherRank(String teacherRank) {
		this.teacherRank = teacherRank;
	}
}
