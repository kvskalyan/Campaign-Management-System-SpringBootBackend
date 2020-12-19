package com.JFSCapstoneBackend.Model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.JFSCapstoneBackend.Service.Constants;

@Entity
@Table(name = "Surveys")
public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Survey_Id")
	private int surveyId;
	@Column(name = "Survey_Name")
	private String surveyName;
	@Column(name = "Survey_Description")
	private String surveyDescription;
	@Column(name = "Survey_Start_Date")
	private long surveyStartDateTime;
	@Column(name = "Survey_End_Date")
	private long surveyEndDateTime;
	@Column(name = "Number_of_Questions")
	private int numberOfQuestions;
	@Column(name = "Survey_Questions", length = 40000)
	private ArrayList<Question> questions = new ArrayList<Question>();
	@Column(name = "Number_of_Responses")
	private int numberOfResponses;
	@Column(name = "Survey_Status")
	private String surveyStatus;

	public Survey() {
		super();
	}

	public Survey(int surveyId, String surveyName, String surveyDescription, long surveyStartDateTime,
			long surveyEndDateTime, int numberOfQuestions, ArrayList<Question> questions, int numberOfResponses) {
		super();
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.surveyDescription = surveyDescription;
		this.surveyStartDateTime = surveyStartDateTime;
		this.surveyEndDateTime = surveyEndDateTime;
		this.numberOfQuestions = numberOfQuestions;
		this.questions = questions;
		this.numberOfResponses = numberOfResponses;
		this.surveyStatus = surveyStartDateTime < surveyEndDateTime ? Constants.ACTIVE : Constants.EXPIRED;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getSurveyDescription() {
		return surveyDescription;
	}

	public void setSurveyDescription(String surveyDescription) {
		this.surveyDescription = surveyDescription;
	}

	public long getSurveyStartDateTime() {
		return surveyStartDateTime;
	}

	public void setSurveyStartDateTime(long surveyStartDateTime) {
		this.surveyStartDateTime = surveyStartDateTime;
	}

	public long getSurveyEndDateTime() {
		return surveyEndDateTime;
	}

	public void setSurveyEndDateTime(long surveyEndDateTime) {
		this.surveyEndDateTime = surveyEndDateTime;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public int getNumberOfResponses() {
		return numberOfResponses;
	}

	public void setNumberOfResponses(int numberOfResponses) {
		this.numberOfResponses = numberOfResponses;
	}

	public String getSurveyStatus() {
		return surveyStatus;
	}

	public void setSurveyStatus(String surveyStatus) {
		this.surveyStatus = surveyStatus;
	}

	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", surveyName=" + surveyName + ", surveyDescription="
				+ surveyDescription + ", surveyStartDateTime=" + surveyStartDateTime + ", surveyEndDateTime="
				+ surveyEndDateTime + ", numberOfQuestions=" + numberOfQuestions + ", questions=" + questions
				+ ", numberOfResponses=" + numberOfResponses + ", surveyStatus=" + surveyStatus + "]";
	}
}