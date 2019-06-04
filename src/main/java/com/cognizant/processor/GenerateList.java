package com.cognizant.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.cognizant.model.Employee;
import com.cognizant.model.Model;

public class GenerateList implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		
		int pageNumber;
		
		Model model = exchange.getIn().getBody(Model.class);
		
		if (exchange.getIn().getHeader("hasNext") == null) {
			exchange.getIn().setHeader("pageNumber", 1);
			exchange.getIn().setHeader("hasNext", true);
			pageNumber = 1;
		} else {
			pageNumber = (int) exchange.getIn().getHeader("pageNumber");
		}

		
		System.out.println(model.getPaging().getHasNextPage());
		
		for (Employee employee : model.getData()) {
			System.out.println(pageNumber + ": " + employee.toString());
		}
		
		if ((boolean) model.getPaging().getHasNextPage()) {
			exchange.getIn().setHeader("pageNumber", ++pageNumber);
		} else {
			exchange.getIn().setHeader("hasNext", false);
		}
	}
}
