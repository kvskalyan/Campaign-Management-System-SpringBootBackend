package com.JFSCapstoneBackend.Service;

import org.springframework.stereotype.Component;

import com.JFSCapstoneBackend.Model.Email;
import com.JFSCapstoneBackend.Model.Response;
import com.JFSCapstoneBackend.Model.Survey;
import com.JFSCapstoneBackend.Model.User;

@Component
public class EmailService {

	public static Email createEmail(User user, Survey survey, Response inputResponse) {
		Email email=new Email();
		email.setTo(user.getUserEmail());
		email.setBody("Hey "+user.getUserName()+",\n\n"+"Thanks for Answering the Survey "+survey.getSurveyName()+"\n");
		return email;
	}
}
