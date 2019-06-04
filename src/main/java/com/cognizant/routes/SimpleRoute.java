package com.cognizant.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import com.cognizant.model.Model;
import com.cognizant.processor.GenerateList;


/*
 * this is the camel route.  it uses a dynamic route as an alternative to loop.
 * the first block is to get page 1.  the second block, the count goes up dynamically,
 * so that all records can be pulled from the called API.
 * 
 * This calls an API, which is paginated, so only a limited number of records will be
 * sent as a response.  The camel route then keeps calling the API, updating the page
 * number to GET.  It processes each response (in this case, console logging each record)
 * until there are no more pages.
 */
@Component
public class SimpleRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		Processor process = new GenerateList();

		JacksonDataFormat restReponse = new JacksonDataFormat(Model.class);

		//BlOCK 1:  initialization and initial call.
		from("timer://simpleTimer?repeatCount=1")
			.setHeader(Exchange.HTTP_METHOD, simple("GET"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.HTTP_QUERY, simple("?sort=+salary&pageSize=2&pageNumber=1"))
				.to("http://localhost:7070/employees")
				.unmarshal(restReponse)
				.process(process)
				.convertBodyTo(String.class)
				//the dynamic Router:  allows for a different process, depending on
				//specific conditions, ie different types of records.
				.dynamicRouter(method(DynamicRouter.class, "slip"))
				.end();

		//BLOCK 2:  allows for the next page to be processed.
		from("direct:router1")
			.setHeader(Exchange.HTTP_METHOD, simple("GET"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.HTTP_QUERY, simple("?sort=+salary&pageSize=2&pageNumber=${header.pageNumber}"))
				.to("http://localhost:7070/employees")
				.unmarshal(restReponse)
				.process(process)
				.convertBodyTo(String.class);
	}
}
