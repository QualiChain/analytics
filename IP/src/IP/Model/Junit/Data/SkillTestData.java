package IP.Model.Junit.Data;

import java.util.ArrayList;
import java.util.List;

import IP.Model.Person;
import IP.Model.Skill;


public class SkillTestData {

	List<Skill> Ss = new ArrayList<Skill>();
	int counter = 0;
	
	public SkillTestData() {
		Ss.add(new Skill("Skill_1", "Test Skill 1", null, null, "Skill comment 1"));
		Ss.add(new Skill("Skill_2", "Test Skill 2", null, null, "Skill comment 2"));
		Ss.add(new Skill("Skill_3", "Test Skill 3", null, null, "Skill comment 3"));
		Ss.add(new Skill("Skill_4", "Test Skill 4", null, null, "Skill comment 4"));
		Ss.add(new Skill("Skill_5", "Test Skill 5", null, null, "Skill comment 5"));
		Ss.add(new Skill("Skill_6", "Test Skill 6", null, null, "Skill comment 6"));
		Ss.add(new Skill("Skill_7", "Test Skill 7", null, null, "Skill comment 7"));
		Ss.add(new Skill("Skill_8", "Test Skill 8", null, null, "Skill comment 8"));
		Ss.add(new Skill("Skill_9", "Test Skill 9", null, null, "Skill comment 9"));
		Ss.add(new Skill("Skill_10", "Test Skill 10", null, null, "Skill comment 10"));
		Ss.add(new Skill("Skill_11", "Test Skill 11", null, null, "Skill comment 11"));
		Ss.add(new Skill("Skill_12", "Test Skill 12", null, null, "Skill comment 12"));
		Ss.add(new Skill("Skill_13", "Test Skill 13", null, null, "Skill comment 13"));
		Ss.add(new Skill("Skill_14", "Test Skill 14", null, null, "Skill comment 14"));
		Ss.add(new Skill("Skill_15", "Test Skill 15", null, null, "Skill comment 15"));
		Ss.add(new Skill("Skill_16", "Test Skill 16", null, null, "Skill comment 16"));
		Ss.add(new Skill("Skill_17", "Test Skill 17", null, null, "Skill comment 17"));
		Ss.add(new Skill("Skill_18", "Test Skill 18", null, null, "Skill comment 18"));
		Ss.add(new Skill("Skill_19", "Test Skill 19", null, null, "Skill comment 19"));
		Ss.add(new Skill("Skill_29", "Test Skill 20", null, null, "Skill comment 20"));
	}
	
	public List<Skill> getSkills(){
		return Ss;
	}
	
	public Skill getSkillByURI(String URI) {
		for(Skill s: Ss) {
			if(s.getURI().equals(URI))
				return s;
		}
		return null;
	}
	
	public Skill getSkillByLabel(String label) {
		for(Skill s: Ss) {
			if(s.getLabel().equals(label))
				return s;
		}
		return null;
	}
	
	//Get different Lists of skills for the different CVs depending on the CV id, for insertion and testing purposes
	//Temporarily returns three skills through a function that rotates between the available skills
	public List<Skill> getSkillsForCVs(String id){
		List<Skill> skills = new ArrayList<Skill>();
		for(int i = 0; i < 3; i++) {
			if(counter < Ss.size()) {
				skills.add(Ss.get(counter++));
			}	
			else {
				counter = 0;
				skills.add(Ss.get(counter++));
			}
		}
		return skills;
	}
	
	//Get different Lists of skills for the different JobPostings depending on the JobPosting id, for insertion and testing purposes
	//Temporarily returns three skills through a function that rotates between the available skills
	public List<Skill> getSkillsForJobPostings(String id){
		List<Skill> skills = new ArrayList<Skill>();
		for(int i = 0; i < 3; i++) {
			if(counter < Ss.size()) {
				skills.add(Ss.get(counter++));
				counter++;
			}	
			else {
				counter = counter % Ss.size();
				skills.add(Ss.get(counter++));
				counter *= 2;
				counter++;
			}
		}
		return skills;
	}
}
