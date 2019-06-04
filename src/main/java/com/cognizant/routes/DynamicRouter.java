package com.cognizant.routes;

import org.apache.camel.Exchange;

public class DynamicRouter {
	public String slip(Exchange exchange) {
		System.out.println("DynamicRouter Page: "+exchange.getMessage().getHeader("pageCount"));
		if(!(boolean) exchange.getIn().getHeader("hasNext")) {
			return null;
		}else {
			return "direct://router1";
		}
	}
}
