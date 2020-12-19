package com.JFSCapstoneBackend.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	private String question;
	private String expectedResponseType;
	private ArrayList<String> options;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getExpectedResponseType() {
		return expectedResponseType;
	}

	public void setExpectedResponseType(String expectedResponseType) {
		this.expectedResponseType = expectedResponseType;
	}

	public ArrayList<String> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}

	public Question(String question, String expectedResponseType, ArrayList<String> options) {
		super();
		this.question = question;
		this.expectedResponseType = expectedResponseType;
		this.options = options;
	}

	public Question(String question, String expectedResponseType) {
		super();
		this.question = question;
		this.expectedResponseType = expectedResponseType;
	}

	public Question() {
		super();
	}

	@Override
	public String toString() {
		return "Question [question=" + question + ", expectedResponseType=" + expectedResponseType + "]";
	}
}
