package org.yk.foodManager.api.status;

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
public class StatusController {

    @Autowired
    StatusRepository statusRepository;

    @GetMapping("/statuses/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable("id") String id) {
        Optional<Status> statusData = statusRepository.findById(id);
        if (statusData.isPresent()) {
            return new ResponseEntity<>(statusData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/statuses")
    @CrossOrigin("*")
    public ResponseEntity<List<Status>> getAllStatuses() {
        try {
            List<Status> statuses = new ArrayList<Status>();
            statusRepository.findAll().forEach(statuses::add);
            if (statuses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(statuses, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/statuses")
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        try {
            Status newStatus = statusRepository.save(status);
            return new ResponseEntity<>(newStatus, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/statuses/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable("id") String id, @RequestBody Status status) {
        Optional<Status> statusData = statusRepository.findById(id);

        try {
            if (statusData.isPresent()) {
                Status updateStatus = statusData.get();
                updateStatus.setStatusId(status.getStatusId());
                updateStatus.setDescription(status.getDescription());
                return new ResponseEntity<>(statusData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
