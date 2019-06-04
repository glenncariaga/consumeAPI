package com.cognizant.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "pageNumber", "pageSize", "totalNumberOfRecords", "totalNumberOfPages", "hasNextPage",
		"hasPreviousPage", "sortingCriteria" })
public class Paging {

	@JsonProperty("pageNumber")
	private Integer pageNumber;
	@JsonProperty("pageSize")
	private Integer pageSize;
	@JsonProperty("totalNumberOfRecords")
	private Integer totalNumberOfRecords;
	@JsonProperty("totalNumberOfPages")
	private Integer totalNumberOfPages;
	@JsonProperty("hasNextPage")
	private Boolean hasNextPage;
	@JsonProperty("hasPreviousPage")
	private Boolean hasPreviousPage;
	@JsonProperty("sortingCriteria")
	private String sortingCriteria;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("pageNumber")
	public Integer getPageNumber() {
		return pageNumber;
	}

	@JsonProperty("pageNumber")
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	@JsonProperty("pageSize")
	public Integer getPageSize() {
		return pageSize;
	}

	@JsonProperty("pageSize")
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@JsonProperty("totalNumberOfRecords")
	public Integer getTotalNumberOfRecords() {
		return totalNumberOfRecords;
	}

	@JsonProperty("totalNumberOfRecords")
	public void setTotalNumberOfRecords(Integer totalNumberOfRecords) {
		this.totalNumberOfRecords = totalNumberOfRecords;
	}

	@JsonProperty("totalNumberOfPages")
	public Integer getTotalNumberOfPages() {
		return totalNumberOfPages;
	}

	@JsonProperty("totalNumberOfPages")
	public void setTotalNumberOfPages(Integer totalNumberOfPages) {
		this.totalNumberOfPages = totalNumberOfPages;
	}

	@JsonProperty("hasNextPage")
	public Boolean getHasNextPage() {
		return hasNextPage;
	}

	@JsonProperty("hasNextPage")
	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	@JsonProperty("hasPreviousPage")
	public Boolean getHasPreviousPage() {
		return hasPreviousPage;
	}

	@JsonProperty("hasPreviousPage")
	public void setHasPreviousPage(Boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	@JsonProperty("sortingCriteria")
	public String getSortingCriteria() {
		return sortingCriteria;
	}

	@JsonProperty("sortingCriteria")
	public void setSortingCriteria(String sortingCriteria) {
		this.sortingCriteria = sortingCriteria;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Paging [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalNumberOfRecords="
				+ totalNumberOfRecords + ", totalNumberOfPages=" + totalNumberOfPages + ", hasNextPage=" + hasNextPage
				+ ", hasPreviousPage=" + hasPreviousPage + ", sortingCriteria=" + sortingCriteria + "]";
	}

}