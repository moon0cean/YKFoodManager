package org.yk.foodManager;

import org.yk.foodManager.api.food.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class WebSocketSessionHandler extends StompSessionHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(WebSocketSessionHandler.class);

    @Override
    public Type getPayloadType(StompHeaders headers) {
        logger.debug("getting payload type");
        return Food.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.debug("HandleFrame for payload: " + payload);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.debug("Connected to websocket");
    }

}
