package rs.ac.uns.ftn.kts.students.service;

import java.util.List;

import rs.ac.uns.ftn.kts.students.model.User;

public interface UserServiccInterface {

	public User findOne(Long id);

	public List<User> findAll();

	public User save(User user);

	public void remove(Long id);

	public User findByUsername(String username);

	public User findByUsernameAndPassword(String username, String password);

}
