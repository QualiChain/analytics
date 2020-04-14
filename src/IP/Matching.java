package IP;

import IP.Model.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Matching {

	private static HashMap<String, Integer> scores;
	private static int weight;

	
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
	 * Uses a JobPosting object (job) to analyze how good of a match each CV object in the available Database is compared to it.
	 * the calculation of each score depends on the JobPosting requirements and the CV qualification
	 * @param job  JobPosting type object that will be analyzed against every CV available in the Database
	 * @return A HashMap with the CV URIs as keys and the corresponding score as value 
	 */
	//TODO: Order cvs by Score
	public static HashMap<String, Integer> getAllCvMatches(JobPosting job){
		scores = new HashMap<String ,Integer>();
		List<CV> allCVs = CV.getCVs();
		for(CV cv : allCVs) {
			scores.put(cv.getURI(), getScore(job, cv));
		}
		
		return scores;
	}
	
	//Generalize to all requirements besides skills, may have to add different more specific classes from a generalized skill class
	/**
	 * Calculates a score to determine how well the CV cv fits the requirement for a JobPosting job
	 * @param job JobPosting class object that will be analyzed for the score calculation
	 * @param cv CV class object that will be scored according to the job requirements
	 * @return Integer representation of how well the CV qualifications matches the JobPosting requirements 
	 */
	private static int getScore(JobPosting job, CV cv) {
		int score = 0;
		List<Skill> requirements = job.getSkillReq();
		Skill toCompare;
		Skill jobReq;
		for(Skill req: requirements) {
			jobReq = Skill.getSkill(req.getUri());
			if(cv.hasSkill(jobReq.getLabel())) {
				score += 1;
			}
			else {
				List<Skill> cvSkills = cv.getSkills();
				for(Skill skill : cvSkills) {
					score += getSkillWeight(jobReq, skill);
				}
			}	
		}
		
		List<String> Caprequirements = job.getCapabilityReq();
		
		for(String req :Caprequirements) {
			List<String> courses = cv.getCourses();
			for(String i : courses) {
				if(i.equalsIgnoreCase(req)) {
					score += 1;
				}
				else
					score -= 1;
			}
		}
		
		List<String> Exprequirements = job.getExpertiseReq();
		
		for(String req : Exprequirements) {
			List<String> education = cv.getEducation();
			for(String i : education) {
				if(i.equalsIgnoreCase(req)) {
					score += 1;
				}
				else
					score -= 1;
					
			}
		}
		
		List<String> Knwrequirements = job.getKnowledgeReq();
		
		for(String req : Knwrequirements) {
			List<String> workHistory = cv.getWorkHistory();
			for(String i : workHistory) {
				if(i.equalsIgnoreCase(req)) {
					score += 1;
				}
				else
					score -= 1;
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
		String reqSkillProficiency = jobReq.getProficiencyLevel();
		String reqSkillPriority = jobReq.getPriorityLevel();
		String cvSkillProficiency = toCompare.getProficiencyLevel();
		
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
		
		if(reqSkillProficiency.equalsIgnoreCase(cvSkillProficiency) ) {
			score += 1;
		}
		else {
			switch (reqSkillProficiency) {
				//Need the possibilities of proficiencies to compare
				case "Novice":
					score += 2;
					break;
				case "Junior":
					if(!cvSkillProficiency.equalsIgnoreCase("Novice"))
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
		
		//call the model creation method for each triple agglomerate and then use the models for comparison
		
		return score;
	}
	
	
	
}
