package rs.ac.uns.ftn.kts.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.kts.students.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsernameAndPassword(String username, String password);

}
