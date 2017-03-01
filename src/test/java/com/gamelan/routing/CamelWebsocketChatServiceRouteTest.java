package com.gamelan.routing;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by PeterM on 01/03/2017.
 */
    public class CamelWebsocketChatServiceRouteTest extends CamelSpringTestSupport {

    private final String websocketRoute = "camelWebsocketChatServiceRoute";
    private static final String WEBSOCKET_URI = "//localhost:9292/chat-service";
    @Override
    public String isMockEndpoints() {
        return "*";
    }

    @Test
    public void testApplicationContext() throws Exception {
        CamelWebsocketChatServiceRoute route = (applicationContext.getBean(websocketRoute, CamelWebsocketChatServiceRoute.class));
        assertNotNull(route);

        assertEquals(WEBSOCKET_URI, route.getWebsocketUri());
    }

    @Test
    public void testCamelContext() throws Exception {
        CamelWebsocketChatServiceRoute route = (CamelWebsocketChatServiceRoute)context.getRegistry().lookupByName(websocketRoute);
        assertNotNull(route);
    }

    @Test
    public void testReceivedMessage() throws Exception {

         List<String> receivedMessageList = new ArrayList<>();
         CountDownLatch latch = new CountDownLatch(1);
        CamelWebsocketChatServiceRoute route = (applicationContext.getBean(websocketRoute, CamelWebsocketChatServiceRoute.class));

        AsyncHttpClient c = new AsyncHttpClient();

        WebSocket websocket = c.prepareGet("ws:" + route.getWebsocketUri()).execute(
                new WebSocketUpgradeHandler.Builder()
                        .addWebSocketListener(new WebSocketTextListener() {
                            @Override
                            public void onMessage(String message) {
                                receivedMessageList.add(message);
                                System.out.println("received --> " + message);
                                latch.countDown();
                            }

                            @Override
                            public void onOpen(WebSocket websocket) {
                            }

                            @Override
                            public void onClose(WebSocket websocket) {
                            }

                            @Override
                            public void onError(Throwable t) {
                                t.printStackTrace();
                            }
                        }).build()).get();

        websocket.sendMessage("test");
        assertTrue(latch.await(10, TimeUnit.SECONDS));

        assertEquals(1, receivedMessageList.size());
        assertEquals("test", receivedMessageList.get(0));

        websocket.close();
        c.close();
    }
    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
