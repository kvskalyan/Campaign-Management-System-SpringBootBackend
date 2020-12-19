package com.JFSCapstoneBackend.Model;

import java.io.Serializable;

public class AbstractResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int responseId;
	private String userName;
	private String userEmail;
	private long responseTime;

	public AbstractResponse() {
		super();
	}

	public AbstractResponse(int responseId, String userName, String userEmail, long responseTime) {
		super();
		this.responseId = responseId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.responseTime = responseTime;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "AbstractResponse [responseId=" + responseId + ", userName=" + userName + ", userEmail=" + userEmail
				+ ", responseTime=" + responseTime + "]";
	}

}
