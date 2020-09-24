package rs.ac.uns.ftn.kts.students.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.kts.students.model.Document;
import rs.ac.uns.ftn.kts.students.model.Student;
import rs.ac.uns.ftn.kts.students.service.DocumentService;
import rs.ac.uns.ftn.kts.students.service.StudentService;
import rs.ac.uns.ftn.kts.students.web.dto.DocumentDTO;
import rs.ac.uns.ftn.kts.students.web.dto.StudentDTO;

@RestController
@RequestMapping(value = "api/documents")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
public class DocumentController {

	@Autowired
	StudentService studentService;

	@Autowired
	DocumentService documentService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
		List<Document> documents = documentService.findAll();
		//convert documents to DTOs
		List<DocumentDTO> documentsDTO = new ArrayList<>();
		for (Document d : documents) {
			documentsDTO.add(new DocumentDTO(d));
		}
		return new ResponseEntity<>(documentsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<DocumentDTO> createDocument(@RequestBody DocumentDTO documentDTO) {
		// a new document must have student defined
		if (documentDTO.getStudent() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Student student = studentService.findOne(documentDTO.getStudent().getId());
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Document document = new Document();
		document.setNaziv(documentDTO.getNaziv());
		document.setStudent(student);

		document = documentService.save(document);
		return new ResponseEntity<>(new DocumentDTO(document), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<DocumentDTO> updateDocument(@RequestBody DocumentDTO documentDTO) {
		// a document must exist
		Document document = documentService.findOne(documentDTO.getId());
		if (document == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		document.setNaziv(documentDTO.getNaziv());

		document = documentService.save(document);
		return new ResponseEntity<>(new DocumentDTO(document), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
		Document document = documentService.findOne(id);
		if (document != null) {
			documentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
