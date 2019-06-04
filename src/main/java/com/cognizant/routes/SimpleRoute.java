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
		String baseUrl = "http://localhost:7070/employees";
		String sortStr = "?sort=+salary";
		int pageSize = 2;
		String pageSizeStr = "&pageSize=" + pageSize;
		int pageNumber = 5;
		String pageNumberStr = "&pageNumber=" + pageNumber;
		String url = baseUrl + sortStr + pageSizeStr + pageNumberStr;

		from("timer:startRoute?delay=20000")
		.setHeader(Exchange.HTTP_METHOD, simple("GET"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to(url).unmarshal(restReponse)
				.process(process)
				.dynamicRouter(method(DynamicRouter.class, "slip")).end();

		from("direct:router1")
//			.log("{nextUrl}}");
				.to("{{nextUrl}}").setHeader(Exchange.HTTP_METHOD, simple("GET"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json")).unmarshal(restReponse).process(process);
	}
}
