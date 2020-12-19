package com.JFSCapstoneBackend.Controller;

import java.util.List;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.JFSCapstoneBackend.Model.Survey;
import com.JFSCapstoneBackend.Repository.SurveyRepository;
import com.JFSCapstoneBackend.Service.Constants;

@RestController
@CrossOrigin
@RequestMapping("/survey")
public class SurveyController {

	@Autowired
	SurveyRepository sRepo;

	@PostMapping(value = "/createSurvey")
	public int createSurvey(@RequestBody Survey inputSurvey) throws ValidationException {
		boolean validationFlag;
		try {
			validationFlag = sRepo.existsBySurveyName(inputSurvey.getSurveyName());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Error while validating Survey Name");
		}
		if (!validationFlag) {
			try {
				inputSurvey.setSurveyStartDateTime(System.currentTimeMillis());
				inputSurvey.setNumberOfResponses(0);
				inputSurvey.setSurveyStatus(
						(System.currentTimeMillis() < inputSurvey.getSurveyEndDateTime()) ? Constants.ACTIVE
								: Constants.EXPIRED);
				sRepo.save(inputSurvey);
				return inputSurvey.getSurveyId();
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Internal Server Error while crating Survey");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Survey Name Exists");
		}
	}

	@GetMapping()
	public Survey getSurvey(@RequestParam(value = "surveyId") String surveyId) {
		int surveyIdInt = Integer.parseInt(surveyId);
		Survey output = sRepo.findById(surveyIdInt).get();
		if (null != output) {
			output.setSurveyStatus((System.currentTimeMillis() < output.getSurveyEndDateTime()) ? Constants.ACTIVE
					: Constants.EXPIRED);
			sRepo.save(output);
			if (StringUtils.equals(output.getSurveyStatus(), Constants.ACTIVE))
				return sRepo.findById(surveyIdInt).get();
			else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Survey with survey Id " + surveyId + " is Expired");
		} else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Survey Does not exist with survey Id " + surveyId);
	}

	@DeleteMapping()
	public void deleteSurvey(@RequestParam(value = "surveyId") String surveyId) {
		int surveyIdInt = Integer.parseInt(surveyId);
		if (sRepo.existsById(surveyIdInt))
			sRepo.delete(sRepo.findById(surveyIdInt).get());
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Survey Name Not Found");
	}

	@GetMapping("/allSurveys")
	public List<Survey> getAllSurveys() {
		List<Survey> allSurveys = sRepo.findAll();
		for (Survey survey : allSurveys) {
			survey.setSurveyStatus((System.currentTimeMillis() < survey.getSurveyEndDateTime()) ? Constants.ACTIVE
					: Constants.EXPIRED);
		}
		sRepo.saveAll(allSurveys);
		return allSurveys;
	}

}
