package rs.ac.uns.ftn.kts.students.web.dto;

import rs.ac.uns.ftn.kts.students.model.Course;

public class CourseDTO {

	private Long id;
	private String name;
	private boolean active;
	
	public CourseDTO() {
		
	}
	
	public CourseDTO(Course course) {
		this(course.getId(), course.getName());
	}
	
	public CourseDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
