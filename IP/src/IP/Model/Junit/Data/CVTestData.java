package IP.Model.Junit.Data;

import java.util.ArrayList;
import java.util.List;

import IP.Model.CV;
import IP.Model.Skill;

public class CVTestData {

	List<CV> cvs = new ArrayList<CV>();
	
	public CVTestData(){
		
		cvs = new ArrayList<CV>();
		cvs.add(new CV("id1", "Test CV 1", "Comment 1", "CV Title 1", "qc:Person1", "Description 1", "Sector 1", "Optional info 1",
				"600", "Euro", null, null, null, null));
		cvs.add(new CV("id2", "Test CV 2", "Comment 2", "CV Title 2", "qc:Person2", "Description 2", "Sector 1", "Optional info 2",
				"700", "Euro", null, null, null, null));
		cvs.add(new CV("id3", "Test CV 3", "Comment 3", "CV Title 3", "qc:Person3", "Description 3", "Sector 2", "Optional info 3",
				"800", "Euro", null, null, null, null));
		cvs.add(new CV("id4", "Test CV 4", "Comment 4", "CV Title 4", "qc:Person4", "Description 4", "Sector 2", "Optional info 4",
				"900", "Euro", null, null, null, null));
		cvs.add(new CV("id5", "Test CV 5", "Comment 5", "CV Title 5", "qc:Person5", "Description 5", "Sector 2", "Optional info 5",
				"1000", "Euro", null, null, null, null));
		cvs.add(new CV("id6", "Test CV 6", "Comment 6", "CV Title 6", "qc:Person6", "Description 6", "Sector 2", "Optional info 6",
				"1200", "Euro", null, null, null, null));
		cvs.add(new CV("id7", "Test CV 7", "Comment 7", "CV Title 7", "qc:Person7", "Description 7", "Sector 3", "Optional info 7",
				"1250", "USD", null, null, null, null));
		cvs.add(new CV("id8", "Test CV 8", "Comment 8", "CV Title 8", "qc:Person8", "Description 8", "Sector 3", "Optional info 8",
				"1300", "USD", null, null, null, null));
		cvs.add(new CV("id9", "Test CV 9", "Comment 9", "CV Title 9", "qc:Person9", "Description 9", "Sector 3", "Optional info 9",
				"1000", "Euro", null, null, null, null));
		cvs.add(new CV("id10", "Test CV 10", "Comment 10", "CV Title 10", "qc:Person10", "Description 10", "Sector 2", "Optional info 10",
				"2000", "USD", null, null, null, null));
		cvs.add(new CV("id11", "Test CV 11", "Comment 11", "CV Title 11", "qc:Person11", "Description 11", "Sector 4", "Optional info 11",
				"1500", "Euro", null, null, null, null));
		cvs.add(new CV("id12", "Test CV 12", "Comment 12", "CV Title 12", "qc:Person12", "Description 12", "Sector 5", "Optional info 12",
				"1100", "Euro", null, null, null, null));
		cvs.add(new CV("id13", "Test CV 13", "Comment 13", "CV Title 13", "qc:Person13", "Description 13", "Sector 3", "Optional info 13",
				"1600", "USD", null, null, null, null));
		cvs.add(new CV("id14", "Test CV 14", "Comment 14", "CV Title 14", "qc:Person14", "Description 14", "Sector 5", "Optional info 14",
				"1200", "Euro", null, null, null, null));
		addCompetencesToCVs();
	}
	
	public List<CV> getCvs(){
		return cvs;
	}
	
	public CV getCvByURI(String URI) {
		for(CV cv: cvs) {
			if(cv.getURI().equals(URI))
				return cv;
		}
		return null;
	}
	
	public CV getCvByLabel(String label) {
		for(CV cv: cvs) {
			if(cv.getLabel().equals(label))
				return cv;
		}
		return null;
	}
	
	public CV getCvByPerson(String PURI) {
		for(CV cv: cvs) {
			if(cv.getPersonURI().equals(PURI))
				return cv;
		}
		return null;
	}
	
	public void addCompetencesToCVs() {
		SkillTestData skillsData = new SkillTestData();
		for(CV cv: cvs) {
			cv.addSkills(skillsData.getSkillsForCVs(cv.getID()));
		}
	}
	
}
