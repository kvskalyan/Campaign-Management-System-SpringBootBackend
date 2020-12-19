package com.JFSCapstoneBackend.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.JFSCapstoneBackend.Model.AbstractResponse;
import com.JFSCapstoneBackend.Model.Response;
import com.JFSCapstoneBackend.Model.Survey;
import com.JFSCapstoneBackend.Model.User;
import com.JFSCapstoneBackend.Repository.ResponseRepository;
import com.JFSCapstoneBackend.Repository.SurveyRepository;
import com.JFSCapstoneBackend.Repository.UserRepository;
import com.JFSCapstoneBackend.Service.EmailService;

@RestController
@CrossOrigin
@RequestMapping("/response")
public class ResponseController {

	@Autowired
	ResponseRepository rRepo;

	@Autowired
	SurveyRepository sRepo;

	@Autowired
	UserRepository uRepo;

	@Autowired
	JmsTemplate jmsTemplate;

	@PostMapping(value = "/saveResponse")
	public int saveResponse(@RequestBody Response inputResponse) throws ValidationException {
		boolean validationFlag;
		User user = null;
		Survey survey = null;
		try {
			validationFlag = sRepo.existsById(inputResponse.getSurveyId());
			if (validationFlag) {
				survey = sRepo.findById(inputResponse.getSurveyId()).get();
				validationFlag = uRepo.existsById(inputResponse.getUserId());
				if (uRepo.existsById(inputResponse.getUserId()))
					user = uRepo.findById(inputResponse.getUserId()).get();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Error while validating Survey and User");
		}
		if (validationFlag) {
			try {
				validationFlag = rRepo.existsBySurveyIdAndUserId(inputResponse.getSurveyId(),
						inputResponse.getUserId());
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Internal Server Error while validating Response");
			}
		}
		if (validationFlag) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user has already answered the survey");
		} else {
			inputResponse.setResponseTime(System.currentTimeMillis());
			rRepo.save(inputResponse);
			Survey answeredSurvey = sRepo.findById(inputResponse.getSurveyId()).get();
			answeredSurvey.setNumberOfResponses(answeredSurvey.getNumberOfResponses() + 1);
			sRepo.save(answeredSurvey);
			jmsTemplate.convertAndSend("JMSQueue", EmailService.createEmail(user,survey,inputResponse));
			return inputResponse.getResponseId();
		}
	}

	@GetMapping(value = "/allResponses")
	public List<AbstractResponse> getAllResponses(@RequestParam(value = "surveyId") String surveyId) {
		int surveyIdInt = Integer.parseInt(surveyId);
		List<AbstractResponse> output = new ArrayList<>();
		List<Response> responsesList = rRepo.findAllBySurveyId(surveyIdInt);
		List<Integer> userIds = responsesList.stream().map(response -> response.getUserId())
				.collect(Collectors.toList());
		List<User> usersList = uRepo.findAllById(userIds);
		responsesList.stream().forEach(response -> output.add(addAbstractResponse(response, usersList)));
		return output;
	}

	private AbstractResponse addAbstractResponse(Response response, List<User> usersList) {
		User u = usersList.stream().filter(user -> response.getUserId() == user.getUserId()).findAny().orElse(null);
		return new AbstractResponse(response.getResponseId(), u.getUserName(), u.getUserEmail(),
				response.getResponseTime());
	}

	@GetMapping
	public Response getResponse(@RequestParam(value = "surveyId") String surveyId,
			@RequestParam(value = "responseId") String responseId) {
		int surveyIdInt = Integer.parseInt(surveyId);
		int responseIdInt = Integer.parseInt(responseId);
		Response output = rRepo.findBySurveyIdAndResponseId(surveyIdInt, responseIdInt);
		if (output == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user has not answered the survey");
		return output;
	}
	
	@GetMapping(value = "/allCompleteResponses")
	public List<Response> getAllCompleteResponses(@RequestParam(value = "surveyId") String surveyId) {
		int surveyIdInt = Integer.parseInt(surveyId);
		List<Response> responsesList = rRepo.findAllBySurveyId(surveyIdInt);
		return responsesList;
	}
}
