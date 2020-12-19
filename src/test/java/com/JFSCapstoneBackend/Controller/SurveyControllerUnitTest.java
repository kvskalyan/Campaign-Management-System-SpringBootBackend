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
import org.springframework.web.server.ResponseStatusException;

import com.JFSCapstoneBackend.testUtil;
import com.JFSCapstoneBackend.Model.Survey;
import com.JFSCapstoneBackend.Repository.SurveyRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SurveyControllerUnitTest extends testUtil{
	
	@Mock
	SurveyRepository sRepo;
	
	@InjectMocks
	SurveyController surveyController;
	
	Survey survey;
	
	@Before
	public void init() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
		survey = (Survey) convertJsonToObject("testSurvey.json", Survey.class);
	}
	
	@Test
	public void test_createSurvey() {
		when(sRepo.existsBySurveyName(any())).thenReturn(false);
		assertNotNull(surveyController.createSurvey(survey));
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_createSurvey1() {
		when(sRepo.existsBySurveyName(any())).thenReturn(true);
		surveyController.createSurvey(survey);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_createSurvey2() {
		when(sRepo.existsBySurveyName(any())).thenThrow(new NoSuchElementException());
		surveyController.createSurvey(survey);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_createSurvey3() {
		when(sRepo.existsBySurveyName(any())).thenReturn(false);
		when(sRepo.save(any())).thenReturn(new NoSuchElementException());
		surveyController.createSurvey(survey);
	}
	
	@Test
	public void test_getSurvey() {
		when(sRepo.findById(any())).thenReturn(Optional.of(survey));
		assertNotNull(surveyController.getSurvey("1"));
	}
	
	@Test
	public void test_deleteSurvey() {
		when(sRepo.existsById(any())).thenReturn(true);
		when(sRepo.findById(any())).thenReturn(Optional.of(survey));
		surveyController.deleteSurvey("1");
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_deleteSurvey1() {
		when(sRepo.existsById(any())).thenReturn(false);
		surveyController.deleteSurvey("1");
	}
	
	@Test
	public void test_getAllSurveys() {
		when(sRepo.findAll()).thenReturn(List.of(survey));
		surveyController.getAllSurveys();
	}
}
