package rs.ac.uns.ftn.kts.students.web.dto;

import java.util.Date;

import rs.ac.uns.ftn.kts.students.model.ExamPeriod;

public class ExamPeriodDTO {
	private Long id;
	private String name;
	private Date startDate;
	private Date endDate;
	
	public ExamPeriodDTO() {
		
	}

	public ExamPeriodDTO(ExamPeriod examPeriod) {
		this(examPeriod.getId(), examPeriod.getName(), examPeriod
				.getStartDate(), examPeriod.getEndDate());
	}

	public ExamPeriodDTO(Long id, String name, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
