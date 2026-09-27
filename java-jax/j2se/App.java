package com.example;

import javax.xml.ws.Endpoint;


public class App {
    public static void main(String[] args) {
        String url = "http://localhost:8080/calculator";
        System.out.println("Publishing standalone service to: " + url);
        Endpoint.publish(url, new Calculator());
        System.out.println("Service running! Access WSDL at: " + url + "?wsdl");
    }
}	