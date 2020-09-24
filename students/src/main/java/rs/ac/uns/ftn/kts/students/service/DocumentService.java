package rs.ac.uns.ftn.kts.students.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.kts.students.model.Document;
import rs.ac.uns.ftn.kts.students.repository.DocumentRepository;

public class DocumentService {
	@Autowired
	DocumentRepository documentRepository;
	
	public Document findOne(Long id) {
		return documentRepository.findById(id).orElse(null);
	}

	public List<Document> findAll() {
		return documentRepository.findAll();
	}
	
	public Page<Document> findAll(Pageable page) {
		return documentRepository.findAll(page);
	}

	public Document save(Document document) {
		return documentRepository.save(document);
	}

	public void remove(Long id) {
		documentRepository.deleteById(id);
	}
}
