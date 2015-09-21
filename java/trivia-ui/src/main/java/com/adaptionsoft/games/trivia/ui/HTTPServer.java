package com.adaptionsoft.games.trivia.ui;

import com.adaptionsoft.games.trivia.ui.event.EventProducer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

class HTTPServer implements Runnable, HttpHandler {
    private final EventProducer producer;

    HTTPServer(EventProducer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", 1981), 0);
            httpServer.createContext("/event", this);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!"POST".equals(httpExchange.getRequestMethod())) {
            return;
        }
        String currentLine;
        try (BufferedReader body = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()))) {
            while ((currentLine = body.readLine()) != null) {
                producer.produce(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpExchange.sendResponseHeaders(200, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpExchange.close();
    }
}
