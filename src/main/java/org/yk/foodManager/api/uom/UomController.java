package org.yk.foodManager.api.uom;

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
public class UomController {

    @Autowired
    UomRepository uomRepository;

    @GetMapping("/uoms/{id}")
    public ResponseEntity<Uom> getUomById(@PathVariable("id") String id) {
        Optional<Uom> uomData = uomRepository.findById(id);
        if (uomData.isPresent()) {
            return new ResponseEntity<>(uomData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/uoms")
    @CrossOrigin("*")
    public ResponseEntity<List<Uom>> getAllUoms() {
        try {
            List<Uom> statuses = new ArrayList<Uom>();
            uomRepository.findAll().forEach(statuses::add);
            if (statuses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(statuses, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uoms")
    public ResponseEntity<Uom> createUom(@RequestBody Uom uom) {
        try {
            Uom newUom = uomRepository.save(uom);
            return new ResponseEntity<>(newUom, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/uoms/{id}")
    public ResponseEntity<Uom> updateContainer(@PathVariable("id") String id, @RequestBody Uom uom) {
        Optional<Uom> uomData = uomRepository.findById(id);

        try {
            if (uomData.isPresent()) {
                Uom updateUom = uomData.get();
                updateUom.setUomType(uom.getUomType());
                updateUom.setDescription(uom.getDescription());
                updateUom.setUomId(uom.getUomId());
                updateUom.setValue(uom.getValue());
                return new ResponseEntity<>(uomData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
