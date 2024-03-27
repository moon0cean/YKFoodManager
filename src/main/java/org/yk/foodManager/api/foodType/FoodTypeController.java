package org.yk.foodManager.api.foodType;

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
public class FoodTypeController {

    @Autowired
    FoodTypeRepository foodTypeRepository;

    @GetMapping("/foodtypes/{id}")
    public ResponseEntity<FoodType> getFoodById(@PathVariable("id") String id) {
        Optional<FoodType> foodData = foodTypeRepository.findById(id);
        if (foodData.isPresent()) {
            return new ResponseEntity<>(foodData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/foodtypes")
    @CrossOrigin("*")
    public ResponseEntity<List<FoodType>> getAllFoodTypes() {
        try {
            List<FoodType> foodTypes = new ArrayList<FoodType>();
            foodTypeRepository.findAll().forEach(foodTypes::add);
            if (foodTypes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foodTypes, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/foodtypes")
    public ResponseEntity<FoodType> createFoodType(@RequestBody FoodType foodType) {
        try {
            FoodType newFoodType = foodTypeRepository.save(foodType);
            return new ResponseEntity<>(newFoodType, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/foodtypes/{id}")
    public ResponseEntity<FoodType> updateFoodType(@PathVariable("id") String id, @RequestBody FoodType foodType) {
        Optional<FoodType> foodTypeData = foodTypeRepository.findById(id);

        try {
            if (foodTypeData.isPresent()) {
                FoodType updateFoodType = foodTypeData.get();
                updateFoodType.setTypeId(foodType.getTypeId());
                updateFoodType.setDescription(foodType.getDescription());
                return new ResponseEntity<>(foodTypeData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
