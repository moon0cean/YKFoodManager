package org.yk.foodManager.api.food;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;


public class FoodWebSocketSessionHandler extends StompSessionHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(FoodWebSocketSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return FoodResponse.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("Received: {}", ((FoodResponse) payload).getStatusId());
    }
}