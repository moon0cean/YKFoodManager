package org.yk.foodManager.api.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id) {
        Optional<Customer> statusData = customerRepository.findById(id);
        if (statusData.isPresent()) {
            return new ResponseEntity<>(statusData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customers")
    @CrossOrigin("*")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = new ArrayList<Customer>();
            customerRepository.findAll().forEach(customers::add);
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCreate(@RequestBody Customer customer) {
        try {
            Customer newCustomer = customerRepository.save(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") String id, @RequestBody Customer customer) {
        Optional<Customer> customerData = customerRepository.findById(id);

        try {
            if (customerData.isPresent()) {
                Customer updateCustomer = customerData.get();
                updateCustomer.setName(customer.getName());
                updateCustomer.setLastName(customer.getLastName());
                updateCustomer.setUserName(customer.getUserName());
                updateCustomer.setPassword(customer.getPassword());
                return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
