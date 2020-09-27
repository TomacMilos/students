package rs.ac.uns.ftn.kts.students.web.dto;

import rs.ac.uns.ftn.kts.students.model.Authority;
import rs.ac.uns.ftn.kts.students.model.User;

public class UserDTO {
	private Long id;
	private String username;
	private String password;
	private Authority authority;
	private Long studentid;
	private Long teacherid;

	public UserDTO() {
	}

	public UserDTO(User user) {
		this(user.getId(), user.getUsername(), user.getPassword(), user.getAuthority(), user.getStudent().getId(),
				user.getStudent().getId());
	}

	public UserDTO(Long id, String username, String password, Authority authority, Long studentid, Long teacherid) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authority = authority;
		this.studentid = studentid;
		this.teacherid = teacherid;
	}

	public Long getId() {
		return id;
	}

	public Long getStudentid() {
		return studentid;
	}

	public void setStudentid(Long studentid) {
		this.studentid = studentid;
	}

	public Long getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(Long teacherid) {
		this.teacherid = teacherid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

}
