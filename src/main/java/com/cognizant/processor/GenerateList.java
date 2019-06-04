package com.cognizant.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

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
		
		//convert messge body into POJO
		Model model = exchange.getIn().getBody(Model.class);
		
		//set headers or modify them
		if (exchange.getIn().getHeader("hasNext") == null) {
			exchange.getIn().setHeader("pageNumber", 1);
			exchange.getIn().setHeader("hasNext", true);
			pageNumber = 1;
		} else {
			pageNumber = (int) exchange.getIn().getHeader("pageNumber");
		}
		
		//process the records
		for (Employee employee : model.getData()) {
			System.out.println(pageNumber + ": " + employee.toString());
		}
		
		//post processing:  is there a next page?
		if ((boolean) model.getPaging().getHasNextPage()) {
			exchange.getIn().setHeader("pageNumber", ++pageNumber);
		} else {
			exchange.getIn().setHeader("hasNext", false);
		}
	}
}
