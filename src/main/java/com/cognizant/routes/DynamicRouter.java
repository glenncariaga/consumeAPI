package com.cognizant.routes;

import org.apache.camel.Exchange;

public class DynamicRouter {
	public String slip(Exchange exchange) {
		System.out.println("DynamicRouter"+exchange.getProperty("nextUrl"));
		if(!(boolean) exchange.getProperty("hasNext")) {
			return null;
		}else {
			return "direct://router1";
		}
	}
}
