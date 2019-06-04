package com.cognizant.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import com.cognizant.model.Model;
import com.cognizant.processor.GenerateList;

@Component
public class SimpleRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		Processor process = new GenerateList();

		JacksonDataFormat restReponse = new JacksonDataFormat(Model.class);

		from("timer://simpleTimer?repeatCount=1")
		.setHeader(Exchange.HTTP_METHOD, simple("GET"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.setHeader(Exchange.HTTP_QUERY, simple("?sort=+salary&pageSize=2&pageNumber=1"))
				.to("http://localhost:7070/employees")
				.unmarshal(restReponse)
				.process(process)
				.convertBodyTo(String.class)
				.dynamicRouter(method(DynamicRouter.class, "slip"))
				.end();

		from("direct:router1")
//			.log("{nextUrl}}");
		.setHeader(Exchange.HTTP_METHOD, simple("GET"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.setHeader(Exchange.HTTP_QUERY, simple("?sort=+salary&pageSize=2&pageNumber=${header.pageNumber}"))
				.to("http://localhost:7070/employees")
				.unmarshal(restReponse)
				.process(process)
				.convertBodyTo(String.class);
	}
}
