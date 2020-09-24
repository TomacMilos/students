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

import rs.ac.uns.ftn.kts.students.model.Payment;
import rs.ac.uns.ftn.kts.students.model.Student;
import rs.ac.uns.ftn.kts.students.service.PaymentService;
import rs.ac.uns.ftn.kts.students.service.StudentService;
import rs.ac.uns.ftn.kts.students.web.dto.PaymentDTO;

@RestController
@RequestMapping(value = "api/payments")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
public class PaymentController {
	
	@Autowired
	StudentService studentService;

	@Autowired
	PaymentService paymentService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<PaymentDTO>> getAllPayments() {
		List<Payment> payments = paymentService.findAll();
		//convert payments to DTOs
		List<PaymentDTO> paymentsDTO = new ArrayList<>();
		for (Payment p : payments) {
			paymentsDTO.add(new PaymentDTO(p));
		}
		return new ResponseEntity<>(paymentsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id){
		Payment payment = paymentService.findOne(id);
		if(payment == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new PaymentDTO(payment), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
		// a new payment must have student defined
		if (paymentDTO.getStudent() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Student student = studentService.findOne(paymentDTO.getStudent().getId());
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Payment payment = new Payment();
		payment.setSvrhaUplate(paymentDTO.getSvrhaUplate());
		payment.setVrednostUplate(paymentDTO.getVrednostUplate());
		payment.setDate(paymentDTO.getDate());
		payment.setStudent(student);

		payment = paymentService.save(payment);
		return new ResponseEntity<>(new PaymentDTO(payment), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<PaymentDTO> updatePayment(@RequestBody PaymentDTO paymentDTO) {
		// a payment must exist
		Payment payment = paymentService.findOne(paymentDTO.getId());
		if (payment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		payment.setSvrhaUplate(paymentDTO.getSvrhaUplate());

		payment = paymentService.save(payment);
		return new ResponseEntity<>(new PaymentDTO(payment), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
		Payment payment = paymentService.findOne(id);
		if (payment != null) {
			paymentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
