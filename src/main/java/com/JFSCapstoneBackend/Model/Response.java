package com.JFSCapstoneBackend.Model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Responses")
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Response_Id")
	private int responseId;
	@Column(name = "User_Id")
	private int userId;
	@Column(name = "Survey_Id")
	private int surveyId;
	@Column(name = "Response_Time")
	private long responseTime;
	@Column(name = "Answers", length = 40000)
	private ArrayList<Answer> answers = new ArrayList<Answer>();

	public Response() {
		super();
	}

	public Response(int responseId, int userId, int surveyId, long responseTime, ArrayList<Answer> answers) {
		super();
		this.responseId = responseId;
		this.userId = userId;
		this.surveyId = surveyId;
		this.responseTime = responseTime;
		this.answers = answers;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Response [responseId=" + responseId + ", userId=" + userId + ", surveyId=" + surveyId
				+ ", responseTime=" + responseTime + ", answers=" + answers + "]";
	}

}
