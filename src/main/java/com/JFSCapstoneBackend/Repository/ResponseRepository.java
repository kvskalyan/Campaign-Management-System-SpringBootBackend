package com.JFSCapstoneBackend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.JFSCapstoneBackend.Model.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Integer> {

	@Query("select count(i)>0 FROM Response i WHERE i.surveyId=?1 and i.userId =?2")
	public boolean existsBySurveyIdAndUserId(@Param("surveyId") int surveyId, @Param("userId") int userId);

	@Query("SELECT i FROM Response i WHERE i.surveyId=?1 and i.userId =?2")
	public Response findBySurveyIdAndUserId(@Param("surveyId") int surveyId, @Param("userId") int userId);
	
	public List<Response> findAllBySurveyId(int surveyId);
	
	public Response findBySurveyIdAndResponseId(int surveyId,int responseId);

}
