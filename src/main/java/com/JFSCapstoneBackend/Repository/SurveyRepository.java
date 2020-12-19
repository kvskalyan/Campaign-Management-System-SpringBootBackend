package com.JFSCapstoneBackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JFSCapstoneBackend.Model.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

	public Survey findBySurveyName(String surveyName);

	public boolean existsBySurveyName(String surveyName);

	public void deleteBySurveyName(String surveyName);

}
