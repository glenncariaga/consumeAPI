package com.cognizant.routes;

import org.apache.camel.Exchange;
/*
 * this is an alternative to a camel loop.  using specific conditions (in this case, 
 * looking for the "hasNext" variable, keep repeating the route till there is no
 * next
 */
public class DynamicRouter {
	public String slip(Exchange exchange) {
		if(!(boolean) exchange.getIn().getHeader("hasNext")) {
			return null;
		}else {
			return "direct://router1";
		}
	}
}
