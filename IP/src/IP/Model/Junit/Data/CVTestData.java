package IP.Model.Junit.Data;

import java.util.ArrayList;
import java.util.List;

import IP.Model.Application;
import IP.Model.CV;
import IP.Model.Course;
import IP.Model.Education;
import IP.Model.Skill;
import IP.Model.WorkHistory;

public class CVTestData {

	List<CV> cvs = new ArrayList<CV>();
	
	public CVTestData(){
		
		cvs = new ArrayList<CV>();
		cvs.add(new CV("id1", "Test CV 1", "Comment 1", "CV Title 1", "qc:1", "Description 1", "Sector 1", "Optional info 1",
				"600", "Euro", null, null, null, null, new Application("qc:app1", "qc:1", "saro:Job1", "1000", "01/01/21", "Euro")));
		cvs.add(new CV("id2", "Test CV 2", "Comment 2", "CV Title 2", "qc:2", "Description 2", "Sector 1", "Optional info 2",
				"700", "Euro", null, null, null, null, new Application("qc:app2", "qc:2", "saro:Job2", "750", "01/01/21", "Euro")));
		cvs.add(new CV("id3", "Test CV 3", "Comment 3", "CV Title 3", "qc:3", "Description 3", "Sector 2", "Optional info 3",
				"800", "Euro", null, null, null, null, new Application("qc:app3", "qc:3", "saro:Job3", "600", "01/01/21", "Euro")));
		cvs.add(new CV("id4", "Test CV 4", "Comment 4", "CV Title 4", "qc:4", "Description 4", "Sector 2", "Optional info 4",
				"900", "Euro", null, null, null, null, new Application("qc:app4", "qc:4", "saro:Job4", "800", "01/01/21", "Euro")));
		cvs.add(new CV("id5", "Test CV 5", "Comment 5", "CV Title 5", "qc:5", "Description 5", "Sector 2", "Optional info 5",
				"1000", "Euro", null, null, null, null, new Application("qc:app5", "qc:5", "saro:Job5", "1200", "01/01/21", "Euro")));
		cvs.add(new CV("id6", "Test CV 6", "Comment 6", "CV Title 6", "qc:6", "Description 6", "Sector 2", "Optional info 6",
				"1200", "Euro", null, null, null, null, new Application("qc:app6", "qc:6", "saro:Job6", "1300", "01/01/21", "Euro")));
		cvs.add(new CV("id7", "Test CV 7", "Comment 7", "CV Title 7", "qc:7", "Description 7", "Sector 3", "Optional info 7",
				"1250", "USD", null, null, null, null, new Application("qc:app7", "qc:7", "saro:Job7", "1050", "01/01/21", "Euro")));
		cvs.add(new CV("id8", "Test CV 8", "Comment 8", "CV Title 8", "qc:8", "Description 8", "Sector 3", "Optional info 8",
				"1300", "USD", null, null, null, null, new Application("qc:app8", "qc:8", "saro:Job8", "1250", "01/01/21", "Euro")));
		cvs.add(new CV("id9", "Test CV 9", "Comment 9", "CV Title 9", "qc:9", "Description 9", "Sector 3", "Optional info 9",
				"1000", "Euro", null, null, null, null, new Application("qc:app9", "qc:9", "saro:Job9", "1678", "01/01/21", "Euro")));
		cvs.add(new CV("id10", "Test CV 10", "Comment 10", "CV Title 10", "qc:10", "Description 10", "Sector 2", "Optional info 10",
				"2000", "USD", null, null, null, null, new Application("qc:app10", "qc:10", "saro:Job10", "1467", "01/01/21", "Euro")));
		cvs.add(new CV("id11", "Test CV 11", "Comment 11", "CV Title 11", "qc:11", "Description 11", "Sector 4", "Optional info 11",
				"1500", "Euro", null, null, null, null, new Application("qc:app11", "qc:11", "saro:Job11", "1234", "01/01/21", "Euro")));
		cvs.add(new CV("id12", "Test CV 12", "Comment 12", "CV Title 12", "qc:12", "Description 12", "Sector 5", "Optional info 12",
				"1100", "Euro", null, null, null, null, new Application("qc:app12", "qc:12", "saro:Job12", "879", "01/01/21", "Euro")));
		cvs.add(new CV("id13", "Test CV 13", "Comment 13", "CV Title 13", "qc:13", "Description 13", "Sector 3", "Optional info 13",
				"1600", "USD", null, null, null, null, new Application("qc:app13", "qc:13", "saro:Job1", "577", "01/01/21", "Euro")));
		cvs.add(new CV("id14", "Test CV 14", "Comment 14", "CV Title 14", "qc:14", "Description 14", "Sector 5", "Optional info 14",
				"1200", "Euro", null, null, null, null, new Application("qc:app14", "qc:14", "saro:Job2", "3000", "01/01/21", "Euro")));
		cvs.add(new CV("id98173918", "Example CV x", "No comment", "Albertos CV", "qc:15", "Things Alberto has done", "Something new", "Is a fast learner",
				"1000", "Euros", null, null, null, null, new Application("qc:app98173918", "qc:15", "saro:Job18763218", "2508", "01/01/21", "Euro") ));
		cvs.add(new CV("id17623814", "Example CV y", "No comment", "Joaquims CV", "qc:23", "Interests and hobbies", "Something fresh", "Has a very good work ethic",
				"780", "Canadian Dollar", null, null, null, null, new Application("qc:app17623814", "qc:23", "saro:Job12987681", "5000", "01/01/21", "Euro") ));
		cvs.add(new CV("id57168721", "Example CV z", "Some comment", "Guilherme CV", "qc:55", "Etc", "Something challenging", "Very proactive",
				"900", "Euro", null, null, null, null, new Application("qc:app57168721", "qc:55", "saro:Job198273817", "4355", "01/01/21", "Euro")));
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
