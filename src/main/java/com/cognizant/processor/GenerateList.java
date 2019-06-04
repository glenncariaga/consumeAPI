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
		if (exchange.getProperty("pageNumber") == null) {
			exchange.setProperty("pageNumber", 1);
			exchange.setProperty("hasNext", true);
			pageNumber = 1;
		} else {
			pageNumber = (int) exchange.getProperty("pageNumber");
		}
		String baseUrl = "http://localhost:7070/employees";
		String sortStr = "?sort=+salary";
		int pageSize = 2;
		String pageSizeStr = "&pageSize=" + pageSize;
		String pageNumberStr = "&pageNumber=" + pageNumber;
		String url = baseUrl + sortStr + pageSizeStr + pageNumberStr;

		
		System.out.println(model.getPaging().getHasNextPage());
		System.out.println(exchange.getProperty("nextUrl"));
		for (Employee employee : model.getData()) {
			System.out.println(pageNumber + ": " + employee.toString());
		}
		
		if ((boolean) model.getPaging().getHasNextPage()) {
			exchange.setProperty("pageNumber", ++pageNumber);
			exchange.setProperty("nextUrl", url);
		} else {
			exchange.setProperty("hasNext", false);
		}
	}
}
