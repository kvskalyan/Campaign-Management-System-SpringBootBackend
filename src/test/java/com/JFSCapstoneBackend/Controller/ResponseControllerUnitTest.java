package com.JFSCapstoneBackend.Controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.JFSCapstoneBackend.testUtil;
import com.JFSCapstoneBackend.Model.Response;
import com.JFSCapstoneBackend.Model.Survey;
import com.JFSCapstoneBackend.Model.User;
import com.JFSCapstoneBackend.Repository.ResponseRepository;
import com.JFSCapstoneBackend.Repository.SurveyRepository;
import com.JFSCapstoneBackend.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ResponseControllerUnitTest extends testUtil {

	@Mock
	ResponseRepository rRepo;

	@Mock
	SurveyRepository sRepo;

	@Mock
	UserRepository uRepo;

	@Mock
	JmsTemplate jmsTemplate;

	@InjectMocks
	ResponseController responseController;

	Response response;
	Survey survey;
	User user;

	@Before
	public void init() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
		response = (Response) convertJsonToObject("testResponse.json", Response.class);
		survey = (Survey) convertJsonToObject("testSurvey.json", Survey.class);
		user = (User) convertJsonToObject("testUser.json", User.class);
	}

	@Test
	public void test_saveResponse() {
		when(sRepo.existsById(any())).thenReturn(true);
		when(sRepo.findById(any())).thenReturn(Optional.of(survey));
		when(uRepo.existsById(any())).thenReturn(true);
		when(uRepo.findById(any())).thenReturn(Optional.of(user));
		when(rRepo.existsBySurveyIdAndUserId(any(Integer.class), any(Integer.class))).thenReturn(false);
		assertNotNull(responseController.saveResponse(response));
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_saveResponse1() {
		when(sRepo.existsById(any())).thenThrow(new NoSuchElementException());
		responseController.saveResponse(response);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_saveResponse2() {
		when(sRepo.existsById(any())).thenReturn(true);
		when(sRepo.findById(any())).thenReturn(Optional.of(survey));
		when(uRepo.existsById(any())).thenReturn(true);
		when(uRepo.findById(any())).thenReturn(Optional.of(user));
		when(rRepo.existsBySurveyIdAndUserId(any(Integer.class), any(Integer.class))).thenThrow(new NoSuchElementException());
		responseController.saveResponse(response);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_saveResponse3() {
		when(sRepo.existsById(any())).thenReturn(true);
		when(sRepo.findById(any())).thenReturn(Optional.of(survey));
		when(uRepo.existsById(any())).thenReturn(true);
		when(uRepo.findById(any())).thenReturn(Optional.of(user));
		when(rRepo.existsBySurveyIdAndUserId(any(Integer.class), any(Integer.class))).thenReturn(true);
		responseController.saveResponse(response);
	}
	
	@Test
	public void test_getAllResponses() {
		when(rRepo.findAllBySurveyId(any(Integer.class))).thenReturn(List.of(response));
		when(uRepo.findAllById(any())).thenReturn(List.of(user));
		assertNotNull(responseController.getAllResponses("1"));
	}
	
	@Test
	public void test_getResponse() {
		when(rRepo.findBySurveyIdAndResponseId(any(Integer.class), any(Integer.class))).thenReturn(response);
		assertNotNull(responseController.getResponse("1", "1"));
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_getResponse1() {
		when(rRepo.findBySurveyIdAndResponseId(any(Integer.class), any(Integer.class))).thenReturn(null);
		responseController.getResponse("1", "1");
	}
	
	@Test
	public void test_getAllCompleteResponses() {
		when(rRepo.findAllBySurveyId(any(Integer.class))).thenReturn(List.of(response));
		assertNotNull(responseController.getAllCompleteResponses("1"));
	}
}
