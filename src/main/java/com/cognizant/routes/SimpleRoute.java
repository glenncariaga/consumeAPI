package com.cognizant.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import com.cognizant.model.Employee;
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
		JacksonDataFormat employee = new JacksonDataFormat(Employee.class);
		// BlOCK 1: initialization and initial call.
		from("timer://simpleTimer?repeatCount=1").setHeader(Exchange.HTTP_METHOD, simple("GET"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.setHeader(Exchange.HTTP_QUERY, simple("?sort=+salary&pageSize=5&pageNumber=1"))
				.to("http://localhost:7070/employees").unmarshal(restReponse).process(process)
				.convertBodyTo(String.class)
				// the loop
				.loopDoWhile(simple("${header.hasnext}")).to("direct:router1").end();

		// BLOCK 2: allows for the next page to be processed.
		from("direct:router1").setHeader(Exchange.HTTP_METHOD, simple("GET"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.setHeader(Exchange.HTTP_QUERY, simple("?sort=+salary&pageSize=2&pageNumber=${header.pageNumber}"))
				.to("http://localhost:7070/employees").unmarshal(restReponse).process(process);
		// using the split EIP on the body, then running http request using camel.
//				.convertBodyTo(Employee.class).split().body().marshal(employee).to("direct:writeToEmployee2API");
//		
		// TODO: this would be the camel created endpoint to to access the reference
		// information.
//		from("direct:readAll")
//			.to(endpoint)

//		from("direct:writeToEmployee2API").setHeader(Exchange.HTTP_METHOD, simple("POST"))
//				.setHeader(Exchange.CONTENT_TYPE, constant("application/json")).removeHeader(Exchange.HTTP_QUERY)
//				.to("http://localhost:8090/employee-management/employees").convertBodyTo(String.class);
	}
}
