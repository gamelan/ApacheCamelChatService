package com.gamelan.routing;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by PeterM on 01/03/2017.
 */
    public class CamelWebsocketChatServiceRouteTest extends CamelSpringTestSupport {

    private final String websocketRoute = "camelWebsocketChatServiceRoute";

    @Override
    public String isMockEndpoints() {
        return "*";
    }

    @Test
    public void testApplicationContext() throws Exception {
        CamelWebsocketChatServiceRoute route = (applicationContext.getBean(websocketRoute, CamelWebsocketChatServiceRoute.class));
        assertNotNull(route);
    }

    @Test
    public void testCamelContext() throws Exception {
        CamelWebsocketChatServiceRoute route = (CamelWebsocketChatServiceRoute)context.getRegistry().lookupByName(websocketRoute);
        assertNotNull(route);
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
