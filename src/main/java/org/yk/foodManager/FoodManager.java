package org.yk.foodManager;

import org.yk.foodManager.api.container.Container;
import org.yk.foodManager.api.container.ContainerRepository;
import org.yk.foodManager.api.customer.Customer;
import org.yk.foodManager.api.customer.CustomerRepository;
import org.yk.foodManager.api.foodType.FoodType;
import org.yk.foodManager.api.foodType.FoodTypeRepository;
import org.yk.foodManager.api.status.Status;
import org.yk.foodManager.api.status.StatusRepository;
import org.yk.foodManager.api.uom.Uom;
import org.yk.foodManager.api.uom.UomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@SpringBootApplication
public class FoodManager {

    private static Logger logger = LoggerFactory.getLogger(FoodManager.class);

    @Autowired
    FoodTypeRepository foodTypeRepository;

    @Autowired
    UomRepository uomRepository;

    @Autowired
    ContainerRepository containerRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(FoodManager.class, args);
    }


    @EventListener
    private void initialSeed(ContextRefreshedEvent event) {
        logger.info("FoodManager: Initial seed event");
        foodTypeSeed();
        uomSeed();
        containerSeed();
        statusSeed();
        customerSeed();
    }

    private void statusSeed() {
        // TODO: i18n descriptions
        if (statusRepository.count() == 0) {
            logger.info("NO STATUS FOUND. SEEDING...");
            ArrayList<Status> statuses = new ArrayList<Status>();
            // TODO: bind this with patch http verb?
//            statuses.add(new Status("WAITING", "Waiting"));
            statuses.add(new Status("UPDATED", "Updated"));
            statuses.add(new Status("CREATED", "Created"));
            statuses.add(new Status("DELETED", "Deleted"));
            statusRepository.saveAll(statuses);
        }
    }

    private void containerSeed() {
        // TODO: i18n descriptions
        if (containerRepository.count() == 0) {
            logger.info("NO CONTAINER TYPES FOUND. SEEDING...");
            ArrayList<Container> containers = new ArrayList<Container>();
            containers.add(new Container("BOTTLE", "Bottle"));
            containers.add(new Container("BOX", "Box"));
            containerRepository.saveAll(containers);
        }
    }

    private void foodTypeSeed() {
        // TODO: i18n descriptions
        if (foodTypeRepository.count() == 0) {
            logger.info("NO FOOD TYPES FOUND. SEEDING...");
            ArrayList<FoodType> foodTypes = new ArrayList<FoodType>();
            foodTypes.add(new FoodType("FOOD", "Food"));
            foodTypes.add(new FoodType("BEVERAGE", "Beverage"));
            foodTypes.add(new FoodType("SAUCE", "Sauce"));
            foodTypes.add(new FoodType("SPICE", "Spice"));
            foodTypeRepository.saveAll(foodTypes);
        }
    }

    private void uomSeed() {
        // TODO: i18n descriptions
        if (uomRepository.count() == 0) {
            logger.info("NO UOM FOUND. SEEDING...");
            ArrayList<Uom> uoms = new ArrayList<Uom>();
            uoms.add(new Uom(1, "WEIGHT", "100", "100 Gr."));
            uoms.add(new Uom(2, "WEIGHT", "1000", "1 Kg."));
            uomRepository.saveAll(uoms);
        }
    }

    private void customerSeed() {
        // TODO: i18n descriptions
        if (customerRepository.count() == 0) {
            logger.info("NO CUSTOMER FOUND. SEEDING...");
            ArrayList<Customer> customers = new ArrayList<Customer>();
            customers.add(new Customer("johnDoe", "test12345", "John", "Doe"));
            customers.add(new Customer("janeDoe", "test12345", "Jane", "Doe"));
            customerRepository.saveAll(customers);
        }
    }

}