package org.yk.foodManager.api.food;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class FoodWebSocketController {
    Logger logger = LoggerFactory.getLogger(FoodWebSocketController.class);

    @MessageMapping("/chart")
    @SendTo("/dashboard/food")
    public FoodResponse sendFoodToWebsocket(@Payload Food food) {
        logger.info("Sending food to websocket: " + food.toString());
        Long createdTime = (food.getCreatedTimestamp() == null) ? null : food.getCreatedTimestamp().getTime();
        Long lastUpdatedTime = (food.getLastUpdatedTimestamp() == null) ? null : food.getLastUpdatedTimestamp().getTime();

        if (createdTime != null) {
            createdTime = 60000 * (createdTime / 60000);
        }
        if (lastUpdatedTime != null) {
            lastUpdatedTime = 60000 * (lastUpdatedTime / 60000);
        }
        return new FoodResponse(food.getStatusId().getStatusId(), createdTime, lastUpdatedTime);
    }

    @SuppressWarnings("unused")
    @SubscribeMapping({"/dashboard", "/dashboard/food"})
    public void listen(Message message) throws Exception {
        logger.info("Subscribed");
    }


}
