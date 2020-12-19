package com.JFSCapstoneBackend.Model;

import java.io.Serializable;

public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;

	private String actualResponseType;
	private String response;

	public String getActualResponseType() {
		return actualResponseType;
	}

	public void setActualResponseType(String actualResponseType) {
		this.actualResponseType = actualResponseType;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Answer [actualResponseType=" + actualResponseType + ", response=" + response + "]";
	}

	public Answer(String actualResponseType, String response) {
		super();
		this.actualResponseType = actualResponseType;
		this.response = response;
	}

	public Answer() {
		super();
	}
}
