package org.yk.foodManager.api.container;

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
public class ContainerController {

    @Autowired
    ContainerRepository containerRepository;

    @GetMapping("/containers/{id}")
    public ResponseEntity<Container> getContainerById(@PathVariable("id") String id) {
        Optional<Container> containerData = containerRepository.findById(id);
        if (containerData.isPresent()) {
            return new ResponseEntity<>(containerData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/containers")
    @CrossOrigin("*")
    public ResponseEntity<List<Container>> getAllContainers() {
        try {
            List<Container> containers = new ArrayList<Container>();
            containerRepository.findAll().forEach(containers::add);
            if (containers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(containers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/containers")
    public ResponseEntity<Container> createFoodType(@RequestBody Container container) {
        try {
            Container newFoodType = containerRepository.save(container);
            return new ResponseEntity<>(newFoodType, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/containers/{id}")
    public ResponseEntity<Container> updateContainer(@PathVariable("id") String id, @RequestBody Container container) {
        Optional<Container> containerData = containerRepository.findById(id);

        try {
            if (containerData.isPresent()) {
                Container updateContainer = containerData.get();
                updateContainer.setContainerTypeId(container.getContainerTypeId());
                updateContainer.setDescription(container.getDescription());
                return new ResponseEntity<>(containerData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
