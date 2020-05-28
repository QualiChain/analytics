package IP.Model.Junit.Data;

import java.util.ArrayList;
import java.util.List;

import IP.Model.CV;
import IP.Model.JobPosting;
import IP.Model.Skill;

public class JobPostingTestData {

	List<JobPosting> JPs = new ArrayList<JobPosting>();	
	
	
	public JobPostingTestData(){
		JPs.add(new JobPosting("Job1", "Test JobPosting 1", "JobPosting Comment 1", "Job Description 1", "full-time",
				"Sector 1", "Job Position Name 1", "Listing Organization 1", "Hiring Organization 1", "Location 1", "2020-03-11", "2022-03-11",
				"senior_level", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job2", "Test JobPosting 2", "JobPosting Comment 2", "Job Description 2", "part-time",
				"Sector 1", "Job Position Name 2", "Listing Organization 1", "Hiring Organization 2", "Location 2", "StartDate2", "EndDate2",
				"internership", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job3", "Test JobPosting 3", "JobPosting Comment 3", "Job Description 3", "contract",
				"Sector 1", "Job Position Name 3", "Listing Organization 2", "Hiring Organization 2", "Location 3", "StartDate3", "EndDate3",
				"entity-level", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job4", "Test JobPosting 4", "JobPosting Comment 4", "Job Description 4", "temporary",
				"Sector 2", "Job Position Name 4", "Listing Organization 2", "Hiring Organization 1", "Location 1", "StartDate4", "EndDate4",
				"associate", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job5", "Test JobPosting 5", "JobPosting Comment 5", "Job Description 5", "volunteer",
				"Sector 2", "Job Position Name 5", "Listing Organization 2", "Hiring Organization 2", "Location 2", "StartDate5", "EndDate5",
				"senior_level", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job6", "Test JobPosting 6", "JobPosting Comment 6", "Job Description 6", "internship",
				"Sector 2", "Job Position Name 6", "Listing Organization 1", "Hiring Organization 2", "Location 3", "StartDate6", "EndDate6",
				"director", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job7", "Test JobPosting 7", "JobPosting Comment 7", "Job Description 7", "contract",
				"Sector 2", "Job Position Name 7", "Listing Organization 3", "Hiring Organization 3", "Location 4", "StartDate7", "EndDate7",
				"executive", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job8", "Test JobPosting 8", "JobPosting Comment 8", "Job Description 8", "contract",
				"Sector 3", "Job Position Name 8", "Listing Organization 4", "Hiring Organization 3", "Location 4", "StartDate8", "EndDate8",
				"expert", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job9", "Test JobPosting 9", "JobPosting Comment 9", "Job Description 9", "contract",
				"Sector 4", "Job Position Name 9", "Listing Organization 3", "Hiring Organization 3", "Location 4", "StartDate9", "EndDate9",
				"senior_level", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job10", "Test JobPosting 10", "JobPosting Comment 10", "Job Description 10", "contract",
				"Sector 4", "Job Position Name 10", "Listing Organization 4", "Hiring Organization 3", "Location 5", "StartDate10", "EndDate10",
				"senior_level", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job11", "Test JobPosting 11", "JobPosting Comment 11", "Job Description 11", "contract",
				"Sector 5", "Job Position Name 11", "Listing Organization 4", "Hiring Organization 4", "Location 6", "StartDate11", "EndDate11",
				"senior_level", null, null, null, null, null, null));
		JPs.add(new JobPosting("Job12", "Test JobPosting 12", "JobPosting Comment 12", "Job Description 12", "contract",
				"Sector 5", "Job Position Name 12", "Listing Organization 1", "Hiring Organization 4", "Location 6", "StartDate12", "EndDate12",
				"senior_level", null, null, null, null, null, null));
		
		//Null values test
		ArrayList<Skill> skills = new ArrayList<>();
		skills.add(new Skill(null, "null", "null", null, null));
		JPs.add(new JobPosting(null, "sdfsdfsdf", null, "sdfsdfsdfsdf", "4", null, null, null, null, "sdfsdfsdfs", null, null,
				null, null, null, null, null, null, null));
		
		addCompetenceReq();
	}
	
	public List<JobPosting> getJobPostings(){
		return JPs;
	}
	
	public JobPosting getJobPostingByURI(String URI) {
		for(JobPosting jp: JPs) {
			if(jp.getURI().equals(URI))
				return jp;
		}
		return null;
	}
	
	public JobPosting getJobPostingByLabel(String label) {
		for(JobPosting jp: JPs) {
			if(jp.getLabel().equals(label))
				return jp;
		}
		return null;
	}
	
	public void addCompetenceReq() {
		SkillTestData skillsData = new SkillTestData();
		for(JobPosting jp: JPs) {
			jp.addSkillsReq(skillsData.getSkillsForJobPostings(jp.getID()));
		}
	}
	
}
