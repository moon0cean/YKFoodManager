package org.yk.foodManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketConfigController {
    private static Logger logger = LoggerFactory.getLogger(WebSocketConfigController.class);

    @Value("${food-manager.websocket.url}")
    String foodManagerWebsocketUrl;
    @RequestMapping("/")
    public String index(Model model) throws Exception {
        logger.info("foodManagerWebsocketUrl " + foodManagerWebsocketUrl);
        model.addAttribute("FOOD_MANAGER_WEBSOCKET_URL",
                foodManagerWebsocketUrl);
        return "index";
    }
}
