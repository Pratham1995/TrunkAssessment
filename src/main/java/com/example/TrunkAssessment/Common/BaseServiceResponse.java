package com.example.TrunkAssessment.Common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseServiceResponse {
	private Object responseData;
	
	@JsonProperty("requestType")
	private String requestType;
	
	@JsonProperty("sucess")
	private boolean success;
	
	@JsonProperty("code")
	private Integer code;
	
	@JsonProperty("description")
	private String description;

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
