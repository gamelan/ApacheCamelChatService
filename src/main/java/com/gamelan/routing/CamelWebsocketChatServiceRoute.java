package com.gamelan.routing;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * Implementation of an Apache Camel RouteBuilder. Defines a Camel WebSocket component.
 */
public class CamelWebsocketChatServiceRoute extends RouteBuilder {

    private String websocketUri;
    private String websocketInConfig;
    private String websocketOutConfig;

    private final String websocketUriFormat = "websocket:%s?%s";

    public CamelWebsocketChatServiceRoute(String websocketUri, String websocketInConfig, String websocketOutConfig) {
        this.websocketUri = websocketUri;
        this.websocketInConfig = websocketInConfig;
        this.websocketOutConfig = websocketOutConfig;
}

    @Override
    public void configure() throws Exception {

        //configure websocket
        String websocketInUri = configureWebSocketInUri();
        String websocketOutUri = configureWebSocketOutUri();

        //declare Camel route
        from(websocketInUri)
                .routeId("chat-server-route")
                .log(LoggingLevel.INFO,">> Message received : ${body}")
                .to(websocketOutUri);
    }

    public String getWebsocketUri() {
        return websocketUri;
    }

    public void setWebsocketUri(String websocketUri) {
        this.websocketUri = websocketUri;
    }

    private String configureWebSocketInUri() {
        String websocketInUri = String.format(websocketUriFormat, websocketUri, websocketInConfig);
        return websocketInUri;
    }

    private String configureWebSocketOutUri() {
        String websocketOutUri = String.format(websocketUriFormat, websocketUri, websocketOutConfig);
        return websocketOutUri;
    }
}
