package rs.ac.uns.ftn.kts.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.kts.students.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	Authority findByName(String name);

	Authority findById(int id);
}
