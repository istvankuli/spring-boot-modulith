package com.istvn.todo.gateway.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePageable<T> {
	private List<T> items;

	private int pageSize;

	private int pageNumber;

	@JsonProperty("record_from")
	private int recordFrom;

	@JsonProperty("record_to")
	private int recordTo;

	/*
	public ResponsePageable(List<T> items, int pageSize, int pageNumber) {
		this.items = items;
		this.pageNumber = pageNumber;
		if(items.size() < pageSize) {
			this.pageSize = items.size();
		}else {
			this.pageSize = pageSize;
		}
			
	}*/

}
