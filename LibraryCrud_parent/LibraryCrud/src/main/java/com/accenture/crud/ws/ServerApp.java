package com.accenture.crud.ws;

import javax.xml.ws.Endpoint;

public class ServerApp {

	public static void main(String[] args) {
		
		Endpoint.publish("http://localhost:8888/BookCrudWS", new LibraryCrudWS());
		System.out.println("Web Service BooksCrud started");

	}

}
