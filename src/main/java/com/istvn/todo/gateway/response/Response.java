package com.istvn.todo.gateway.response;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
	private Instant timestamp = Instant.now();
	private String status;
	private String result;
	private ResponseError error;
	private T data;
}
