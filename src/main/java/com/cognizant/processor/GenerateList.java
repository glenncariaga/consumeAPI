package com.cognizant.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cognizant.model.Employee;
import com.cognizant.model.Model;

/*
 * This process takes advantage of camel's converters to create
 * POJO objects from the message body.  
 * this also sets headers, if there was none, and modifies headers
 * for each run of the process (providing meta data) and setting flags
 * necessary to terminate the job.
 */

public class GenerateList implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {

		int pageNumber;

		// convert messge body into POJO
		Model model = exchange.getIn().getBody(Model.class);// check model object null

		// set headers or modify them
		if (exchange.getIn().getHeader("hasNext") == null) {
			exchange.getIn().setHeader("pageNumber", 1);
			exchange.getIn().setHeader("hasNext", true);
			pageNumber = 1;
		} else {
			pageNumber = (int) exchange.getIn().getHeader("pageNumber");
		}

		// process the records
		for (Employee employee : model.getData()) {

			// creation of a new HTTP request to POST the employee to the 2nd API
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("firstName", employee.getFirstName());
			map.add("lastName", employee.getLastName());
			map.add("email", "genericEmail@generic.com");

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

			ResponseEntity<String> response = restTemplate
					.postForEntity("http://localhost:8090/employee-management/employees", request, String.class);
			// take apart the response, get the id that returns.
			System.out.println(response.getBody());
		}

		// post processing: is there a next page?
		if ((boolean) model.getPaging().getHasNextPage()) {
			exchange.getIn().setHeader("pageNumber", ++pageNumber);
		} else {
			exchange.getIn().setHeader("hasNext", false);
		}

		exchange.getIn().setBody(model.getData());
	}
}
