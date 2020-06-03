package IP;

import IP.Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Matching {

	private static HashMap<String, Integer> scores;
	private static int weight;
	private static final int SKILL_COEFICIENT = 5;
	private static final int COURSE_COEFICIENT = 10;
	private static final int EDUCATION_COEFICIENT = 15;
	private static final int WORKHISTORY_COEFICIENT = 20;
	
	/**
	 * Compares the requisites of a job post to each individual CV's Skills in the Database to generate a score
	 * @deprecated Use getAllCvMatches instead
	 * @param jobPost JobPosting class object to be analyzed against
	 * @return Basic score HashMap with CV URIs as keys and their respective scores as values
	 */
	public static HashMap<String, Integer> getMachesScores(JobPosting jobPost) {
		List<String> reqs = jobPost.getJobSkillReqLabels();
		
		scores = new HashMap<String ,Integer>();
		
		weight = 1; 
		
		List<CV> cvsWithSkill;
		int score = 0;
		
		for(String requirement : reqs) {
			cvsWithSkill = CV.getCVsBySkill(requirement);
			for(CV cv : cvsWithSkill) {
				String URI = cv.getURI();
				if(!scores.containsKey(URI)) {
					scores.put(URI, 1*weight);
				}
				else {
					score = scores.get(URI) + 1*weight;
					scores.replace(cv.getURI(), score);
				}
					
			}
		}
		
		return scores;
	}
	
	/**
	 * Uses a JobPosting object (job) to analyze how good of a match each CV object in the available Database, or that has applied for the job
	 * is compared to it.
	 * the calculation of each score depends on the JobPosting requirements and the CV qualification
	 * @param job  JobPosting type object that will be analyzed against every CV available in the Database
	 * @return A HashMap with the CV URIs as keys and the corresponding score as value 
	 */
	//TODO: Order cvs by Score
	public static HashMap<String, Integer> getAllCvMatches(JobPosting job, boolean applied){
		scores = new HashMap<String ,Integer>();
		int score = 0;
		List<CV> cvs;
		List<Application> apps;
		//If we are looking at applied cvs only we get the cvs that applied for the job and use that list
		if(applied) {
			cvs = new ArrayList<CV>();
			apps = job.getApplications();
			for(Application app: apps) {
				cvs.add(CV.getCVbyPersonURI(app.getPersonURI()));
			}
		}
		//Otherwise we get all cvs and fill the list to use for the matching with all of them
		else
			cvs = CV.getCVs();
		
		for(CV cv : cvs) {
			score = getScore(job, cv);
			if(score > 0)
				scores.put(cv.getURI(), score);
		}
		
		return scores;
	}
	
	//Generalize to all requirements besides skills, may have to add different more specific classes from a generalized skill class
	/**
	 * Calculates a score to determine how well the CV cv fits the requirement for a JobPosting job
	 * If a requirement matches completely, score goes up by 2 * "requirement coeficient"
	 * If a requirement matches partially, score goes up by 1 * "requirement coeficient"
	 * If a requirement does not match, score goes down by 1 * "requirement coeficient"
	 * If a requirement is a skill, score will be affected by further analysis of the skill's priority and proficiency
	 * @param job JobPosting class object that will be analyzed for the score calculation
	 * @param cv CV class object that will be scored according to the job requirements
	 * @return Integer representation of how well the CV qualifications matches the JobPosting requirements 
	 */
	private static int getScore(JobPosting job, CV cv) {
		
		int score = 0;
		
		List<Skill> requirements = job.getSkillReq();
		List<Skill> cvSkills = cv.getSkills();
		int tmpScore;
		if(requirements.size() != 0) {
			for(Skill req: requirements) {
				if(cv.hasSkill(req)) {
					score += getSkillWeight(req, req) * SKILL_COEFICIENT;
				}
				else {
					//if the score doesn't change with any skill in the cv then the cv does not contain a related skill to the one
					//being analyzed from the Job Posting, therefore it lowers the overall score by 1
					tmpScore = score;
					if(cvSkills.size() != 0) {
						for(Skill skill : cvSkills) {
							score += getSkillWeight(req, skill) * SKILL_COEFICIENT;
						}
					}
					
					if(score == tmpScore)
						score--;
				}	
			}
		}
		
		
		List<Course> Caprequirements = job.getCapabilityReq();
		if(Caprequirements.size() != 0) {
			for(Course req :Caprequirements) {
				List<Course> courses = cv.getCourses();
				for(Course i : courses) {
					if(i.getLabel().equalsIgnoreCase(req.getLabel())) {
						score += 1 * COURSE_COEFICIENT;
					}
					else
						score -= 1 * COURSE_COEFICIENT;
				}
			}
		}
		
		
		List<Education> Exprequirements = job.getExpertiseReq();
		
		if(Exprequirements.size() != 0) {
			for(Education req : Exprequirements) {
				List<Education> education = cv.getEducation();
				for(Education i : education) {
					if(i.getLabel().equalsIgnoreCase(req.getLabel())) {
						score += 1 * EDUCATION_COEFICIENT;
					}
					else
						score -= 1 * EDUCATION_COEFICIENT;
						
				}
			}
		}
		
		
		List<WorkHistory> Knwrequirements = job.getKnowledgeReq();
		
		if(Knwrequirements.size() != 0) {
			for(WorkHistory req : Knwrequirements) {
				List<WorkHistory> workHistory = cv.getWorkHistory();
				for(WorkHistory i : workHistory) {
					if(i.getLabel().equalsIgnoreCase(req.getLabel())) {
						score += 1 + WORKHISTORY_COEFICIENT ;
					}
					else
						score -= 1 * WORKHISTORY_COEFICIENT;
				}
			}
		}
		
		
		return score;
	}
	
	/**
	 * Calculates and Integer representation of how much a Skill toCompare is worth compared to
	 * the required Skill jobReq
	 * @param jobReq A skill required by a JobPosting
	 * @param toCompare A skill from a CV
	 * @return An integer representation of how valuable the Skill toCompare is in comparison to
	 * the Skill jobReq
	 */
	private static int getSkillWeight(Skill jobReq, Skill toCompare) {
		int score = 0;

		if (jobReq==null)
			return 0;
		else if (jobReq.getLabel()==null)
			return 0; 

		if (toCompare==null)
			return 0;
		else if (toCompare.getLabel()==null)
			return 0; 
		
		//Need a function to understand if they are at least related so it doesn't compare two completely unrelated skills like a
		//Java skill with a Surgery skill, then if they are related continue the method, if not return 0
		//Tries to assess if the two skills are related or not, if they are not related returns 0 immediately
		//Thought process is the following, if they are the same object it automatically says they are related
		//And if the skill labels are the same or one of the labels contains the other, then they are related in some way and the method continues
		if(jobReq != toCompare && !(jobReq.getLabel().equals(toCompare.getLabel()) ||
									jobReq.getLabel().contains(toCompare.getLabel()) ||
									toCompare.getLabel().contains(jobReq.getLabel()))) {
			
			return 0;
		}
		
		String reqSkillProficiency = jobReq.getProficiencyLevel();
		String reqSkillPriority = jobReq.getPriorityLevel();
		String cvSkillProficiency = toCompare.getProficiencyLevel();
		
		
		if(reqSkillPriority != null) {
			//Switch with score association for each case, greater, equals and lower than
			switch(reqSkillPriority) {
				case "High" :
					score += 3;
					break;
				case "Medium" :
					score += 2;
					break;
				case "Low" :
					score += 1;
				default :
					break;
			}
		}
		
		if(reqSkillProficiency != null && cvSkillProficiency != null) {
			if(reqSkillProficiency.equalsIgnoreCase(cvSkillProficiency) ) {
				score += 1;
			}
			else {
				switch (reqSkillProficiency) {
					//Need the possibilities of proficiencies to compare
					case "Basic":
						score += 2;
						break;
					case "Junior":
						if(!cvSkillProficiency.equalsIgnoreCase("Basic"))
							score += 2;
						else
							score -=1;
						break;
					case "Senior":
						if(cvSkillProficiency.equalsIgnoreCase("Expert"))
							score += 2;
						else
							score -=1;
						break;
					case "Expert":
						score -=1;
						break;
					default:
						break;
				}
			}
		}
		
		//call the model creation method for each triple agglomerate and then use the models for comparison
		
		return score;
	}
	
	
	
}
