package org.yk.foodManager.api.food;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.yk.foodManager.api.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api")
public class FoodController {
    Logger logger = LoggerFactory.getLogger(FoodController.class);

    @Autowired
    FoodRepository foodRepository;

    StompSession stompSession;

    final WebSocketStompClient stompClient;

    final StompHeaders stompHeaders;

    @Value("${food-manager.websocket.url}")
    String foodManagerWebsocketUrl;

    FoodController() {
        StompHeaders headers = new StompHeaders();
        headers.setDestination("/app/chart");
        headers.setContentType(new MimeType("application", "json"));

        this.stompHeaders = headers;
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
    }

    @GetMapping("/foods")
    public ResponseEntity<List<Food>> getFoodList() {
        List<Food> foodListData = foodRepository.findAll();
        if (!foodListData.isEmpty()) {
            return new ResponseEntity<>(foodListData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/foods/{id}")
    @CrossOrigin("*")
    public ResponseEntity<Food> getFoodById(@PathVariable("id") long id) {
        Optional<Food> foodData = foodRepository.findById(id);
        if (foodData.isPresent()) {
            return new ResponseEntity<>(foodData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/foods")
    @CrossOrigin("*")
    public ResponseEntity<String> createFood(@RequestBody Food food) {

        try {
            food.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
            Food createdFood = foodRepository.save(food);
            logger.info("Food [" + createdFood.getId() + "] created successfully");

            sendToWebsocket(food);

            return new ResponseEntity<>(String.valueOf(createdFood.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Food creation failed", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/foods/{id}")
    @CrossOrigin("*")
    public ResponseEntity<String> updateFood(@PathVariable("id") long id, @RequestBody Food food) {
        Optional<Food> foodData = foodRepository.findById(id);

        try {
            if (foodData.isPresent()) {
                Food existingFood = foodData.get();

                food.setStatusId(new Status("UPDATED"));
                food.setLastUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
                food.setCreatedBy(existingFood.getCreatedBy());
                Food updatedFood = foodRepository.save(food);
                logger.info("Food [" + updatedFood.getId() + "] updated successfully");

                sendToWebsocket(updatedFood);

                return new ResponseEntity<>(String.valueOf(updatedFood.getId()), HttpStatus.OK);
            } else {
                logger.error("Food [" + foodData.get().getId() + "] not found failed");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Food update failed", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/foods/{id}")
    @CrossOrigin("*")
    public ResponseEntity<String> deleteFood(@PathVariable("id") long id) {
        Optional<Food> foodData = foodRepository.findById(id);

        try {
            if (foodData.isPresent()) {
                Food food = foodData.get();
                food.setStatusId(new Status("DELETED"));
                food.setLastUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
                Food deletedFood = foodRepository.save(food);
                logger.info("Food [" + deletedFood.getId() + "] deleted successfully");

                sendToWebsocket(deletedFood);

                return new ResponseEntity<>(String.valueOf(deletedFood.getId()), HttpStatus.OK);
            } else {
                logger.error("Food [" + foodData.get().getId() + "] not found failed");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Food update failed", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendToWebsocket(Food food) {
        try {
            if (stompSession == null) {
                try {
                    this.stompSession = stompClient.connect(foodManagerWebsocketUrl + "/websocket",
                            new FoodWebSocketSessionHandler()).get();
                    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
                    logger.info("Connection to websocket successful");
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Error connecting to websocket: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            ObjectWriter ow = mapper.writer();
            String json = ow.writeValueAsString(food);
            synchronized (stompSession) {
                stompSession.send(stompHeaders, json.getBytes());
            }
        } catch (Exception e) {
            logger.error("Error sending to websocket: " + e.getMessage());
        }
    }

}
