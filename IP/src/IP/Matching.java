package IP;

import IP.Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Matching {

	private static HashMap<String, Integer> scores;
	private static int weight;
	private static final int SKILL_COEFICIENT = 10;
	private static final int COURSE_COEFICIENT = 10;
	private static final int EDUCATION_COEFICIENT = 10;
	private static final int WORKHISTORY_COEFICIENT = 10;
	
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
		System.out.println("CALCULATING JOB MAX SCORE");
		int highScore = getJobMaxScore(job);
		System.out.println("JOBPOSTING SCORE");
		System.out.println(highScore);
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
			System.out.println(cv.getInfo());
			score = getScore(job, cv);
			if (score>highScore) score = highScore;
			System.out.println("CV SCORE");
			System.out.println(score);
			if(score > 0)
				scores.put(cv.getURI(), (score*100)/highScore);
		}
		return scores;
	}
	
	private static int getJobMaxScore(JobPosting job) {
		int score = 0;
		for(Course cs: job.getCapabilityReq()) {
			score += 1 * COURSE_COEFICIENT;
		}
		for(Education ed: job.getEducationReq()) {
			score += 1 * EDUCATION_COEFICIENT;
		}
		for(WorkHistory wh: job.getworkExperienceReq()) {
			score += 1 * WORKHISTORY_COEFICIENT;
		}
		for(Skill skl: job.getSkillReq()) {
			score += getSkillWeight(skl,skl) * SKILL_COEFICIENT;
		}
		return score;
	}

	//Generalize to all requirements besides skills, may have to add different more specific classes from a generalized skill class
	/**
	 * Calculates a score to determine how well the CV cv fits the requirement for a JobPosting job
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
					
//					if(score == tmpScore)
//						score--;
				}	
			}
		}
		System.out.println("SKILL SCORE");
		System.out.println(score);
		
		boolean hasReq = false;
		List<Course> Caprequirements = job.getCapabilityReq();
		if(Caprequirements.size() != 0) {
			for(Course req :Caprequirements) {
				List<Course> courses = cv.getCourses();
				for(Course i : courses) {
					if(i.getQualification().equalsIgnoreCase(req.getQualification()) ||
							i.getQualification().contains(req.getQualification()) ||
							req.getQualification().contains(i.getQualification())) {
						score += 1 * COURSE_COEFICIENT;
						hasReq = true;
					}
				}
//				if(!hasReq) {
//					score -= 1;
//				}
				hasReq = false;
			}
		}
		
		
		List<Education> Exprequirements = job.getEducationReq();
		
		if(Exprequirements.size() != 0) {
			for(Education req : Exprequirements) {
				List<Education> education = cv.getEducation();
				for(Education i : education) {
					if(i.getTitle().equalsIgnoreCase(req.getTitle())||
							i.getTitle().contains(req.getTitle()) ||
							req.getTitle().contains(i.getTitle()) ||
							i.getDescription().equalsIgnoreCase(req.getDescription())||
							i.getDescription().contains(req.getDescription()) ||
							req.getDescription().contains(i.getDescription())) {
						score += 1 * EDUCATION_COEFICIENT;
						hasReq = true;
					}
				}
//				if(!hasReq) {
//					score -= 1;
//				}
				hasReq = false;
			}
		}
		
		
		List<WorkHistory> Knwrequirements = job.getworkExperienceReq();
		
		if(Knwrequirements.size() != 0) {
			for(WorkHistory req : Knwrequirements) {
				List<WorkHistory> workHistory = cv.getWorkHistory();
				for(WorkHistory i : workHistory) {
					if(i.getPosition().equalsIgnoreCase(req.getPosition())||
							i.getPosition().contains(req.getPosition()) ||
							req.getPosition().contains(i.getPosition())) {
						score += 1 * WORKHISTORY_COEFICIENT ;
						hasReq = true;
					}
						
				}
//				if(!hasReq) {
//					score -= 1;
//				}
				hasReq = false;
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
		if(jobReq != toCompare && !(jobReq.getLabel().equalsIgnoreCase(toCompare.getLabel()) ||
									jobReq.getLabel().toLowerCase().contains(toCompare.getLabel().toLowerCase()) ||
									toCompare.getLabel().toLowerCase().contains(jobReq.getLabel().toLowerCase()))) {
			
			return 0;
		}
		String reqSkillProficiency = null;
		String reqSkillPriority = null;
		String cvSkillProficiency = null;
		if(jobReq.getProficiencyLevel() != null) {
			reqSkillProficiency = jobReq.getProficiencyLevel().toLowerCase();
		}
		if(jobReq.getPriorityLevel() != null) {
			reqSkillPriority = jobReq.getPriorityLevel().toLowerCase();
		}
		if(toCompare.getProficiencyLevel() != null) {
			cvSkillProficiency = toCompare.getProficiencyLevel().toLowerCase();
		}
		
		
		
		if(reqSkillPriority != null) {
			//Switch with score association for each case, greater, equals and lower than
			switch(reqSkillPriority) {
				case "high" :
					score += 3;
					break;
				case "medium" :
					score += 2;
					break;
				case "low" :
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
					case "basic":
						score += 1;
						break;
					case "junior":
						if(!cvSkillProficiency.equalsIgnoreCase("basic"))
							score += 1;
//						else
//							score -=1;
						break;
					case "advanced":
						if(cvSkillProficiency.equalsIgnoreCase("expert"))
							score += 1;
//						else
//							score -=1;
						break;
//					case "expert":
//						score -=1;
//						break;
					default:
						break;
				}
			}
		}
	
		
		return score;
	}
	
	
	
}
