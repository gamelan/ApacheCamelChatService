package com.gamelan.routing;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by PeterM on 01/03/2017.
 */
public class CamelWebsocketChatServiceRoute extends RouteBuilder {

    private String websocketUri;

    public CamelWebsocketChatServiceRoute(String websocketUri) {
        this.websocketUri = websocketUri;
    }

    @Override
    public void configure() throws Exception {
        //do nothing
    }

    public String getWebsocketUri() {
        return websocketUri;
    }

    public void setWebsocketUri(String websocketUri) {
        this.websocketUri = websocketUri;
    }
}
