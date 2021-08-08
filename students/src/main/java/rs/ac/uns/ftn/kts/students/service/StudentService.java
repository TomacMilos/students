package rs.ac.uns.ftn.kts.students.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.kts.students.model.Student;
import rs.ac.uns.ftn.kts.students.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	public Student findOne(Long id) {
		return studentRepository.findById(id).orElse(null);
	}

	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	public Page<Student> findAll(Pageable page) {
		return studentRepository.findAll(page);
	}

	public List<Student> findAllByActive(boolean actiive) {
		return studentRepository.findAllByActive(actiive);
	}

	public Student save(Student student) {
		return studentRepository.save(student);
	}

	public void remove(Long id) {
		studentRepository.deleteById(id);
	}
	
	public Student findByCard(String cardNumber) {
		return studentRepository.findOneByCardNumber(cardNumber);
	}
	
	public List<Student> findByLastName(String lastName) {
		return studentRepository.findAllByLastName(lastName);
	}
	
	public int calculateGrade(int points) {
		if (points < 0 || points > 100)
			throw new NumberFormatException();
		
		return points < 55 ? 5 : Math.round(points/(float) 10);
	}
}
