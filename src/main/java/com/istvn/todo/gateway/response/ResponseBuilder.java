package com.istvn.todo.gateway.response;

public class ResponseBuilder<T> {
	private final Response<T> responseVO = new Response<>();

	private ResponseBuilder<T> result(String result) {
		responseVO.setResult(result);
		return this;
	}

	private ResponseBuilder<T> status(String status) {
		responseVO.setStatus(status);
		return this;
	}

	public ResponseBuilder<T> success() {
		return new ResponseBuilder<T>().result("Succeed").status("200");
	}

	public ResponseBuilder<T> fail(String status) {
		return new ResponseBuilder<T>().result("Failed").status(status);
	}

	public ResponseBuilder<T> error(ResponseError error) {
		responseVO.setError(error);
		return this;
	}

	public ResponseBuilder<T> addData(final T body) {
		responseVO.setData(body);
		responseVO.setResult("Succeeded");
		responseVO.setStatus("200");
		return this;
	}

	public Response<T> build() {
		return responseVO;
	}

}
