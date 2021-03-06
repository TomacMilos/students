package rs.ac.uns.ftn.kts.students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.kts.students.model.Authority;
import rs.ac.uns.ftn.kts.students.repository.AuthorityRepository;

@Service
public class AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;

	public Authority findById(int id) {

		return authorityRepository.findById(id);
	}

	public Authority findByName(String name) {
		return authorityRepository.findByName(name);
	}

}
