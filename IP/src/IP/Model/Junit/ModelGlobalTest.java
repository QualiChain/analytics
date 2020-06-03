package IP.Model.Junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import IP.Matching;
import IP.Model.CV;
import IP.Model.JobPosting;
import IP.Model.Person;
import IP.Model.Skill;
import IP.Model.SparqlEndPoint;
import IP.Model.Junit.Data.CVTestData;
import IP.Model.Junit.Data.JobPostingTestData;
import IP.Model.Junit.Data.PersonTestData;
import IP.Model.Junit.Data.SkillTestData;
import IP.config.Configuration;

@TestMethodOrder(value = OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelGlobalTest {

	Configuration cnf = new Configuration();
	SkillTestData skd = new SkillTestData();
	CVTestData cvd = new CVTestData();
	PersonTestData pd = new PersonTestData();
	JobPostingTestData jpd = new JobPostingTestData();
	
	@BeforeAll
	void init() {
		
		cnf.LoadConfiguration();
		//Reset database
		SparqlEndPoint.deleteTriple("?s ?o ?p", "?s ?o ?p");
		//Make sure database has prefixes
		SparqlEndPoint.insertTTLFile("prefixes.ttl");
	}
	
	@Test
	@Order(1)
	@DisplayName("Attempting to get data that doesn't exist from server")
	void failGetData() {
		assertTrue(CV.getCVs().size() == 0);
		assertTrue(Person.getPersons().size() == 0);
		assertTrue(Skill.getSkills().size() == 0);
		assertTrue(JobPosting.getJobPostings().size() == 0);
	}
	
	@Test
	@Order(2)
	@DisplayName("Attempting to insert data from all model classes to server")
	void insertAllData() {
		List<Skill> skills = skd.getSkills();
		List<CV> cvs = cvd.getCvs();
		List<Person> ps = pd.getPersons();
		List<JobPosting> jps = jpd.getJobPostings();
	
		for(Skill skill: skills) {
			try {
				skill.Save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(JobPosting jp: jps) {
			try {
				jp.Save();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		for(CV cv: cvs) {
			try {
				cv.Save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(Person p : ps) {
			try {
				p.Save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		skills = Skill.getSkills();
		assertTrue(skills.size() == skd.getSkills().size());
		
		cvs = CV.getCVs();
		assertTrue(cvs.size() == cvd.getCvs().size());
		
		ps = Person.getPersons();
		assertTrue(ps.size() == pd.getPersons().size());
		
		jps = JobPosting.getJobPostings();
		assertTrue(jps.size() <= jpd.getJobPostings().size());
			
		
	}
	
	@Test
	@Order(3)
	@DisplayName("Verifying if its possible to recover Personal data from CV, and CVs from Personal data")
	void verifyCVtoPersonAssociation() {
		List<Person> ps = new ArrayList<Person>();
		List<CV> cvs = cvd.getCvs();
		Person tempPerson = null;
		CV tempCV = null;
		for(CV cv: cvs) {
			tempPerson = Person.getPerson(cv.getPersonURI());
			ps.add(tempPerson);
			assertTrue(tempPerson.getLabel() != null);
		}
		assertTrue(ps.size() == pd.getPersons().size());
		
		ps = pd.getPersons();
		cvs = new ArrayList<CV>();
		for(Person p: ps) {
			tempCV = CV.getCV(p.getCVURI());
			cvs.add(tempCV);
			assertTrue(tempCV.getLabel() != null);
		}
		assertTrue(cvs.size() == cvd.getCvs().size());
		
	}
	
	@Test
	@Order(4)
	@DisplayName("Recovering JobPostings by Skill URI")
	@Disabled("Not yet Implemented")
	void getJobPostingsBySkills() {
		
	}
	
	@Test
	@Order(5)
	@DisplayName("Recovering CVs by Skill URI")
	@Disabled("Not yet Implemented")
	void getCVsBySkills() {
		
	}
	
	@Test
	@Order(6)
	@DisplayName("Testing if matching function returns desired results")
	void matchingTest() {
		List<JobPosting> jps = JobPosting.getJobPostings();
		HashMap<String, Integer> matches;
		for(JobPosting jp: jps) {
			matches = Matching.getAllCvMatches(jp, false);
			assertFalse(matches.containsValue(0));
			assertTrue(matches.size() <= cvd.getCvs().size());
		}
	}
	
	@Test
	@Order(7)
	@DisplayName("Testing Delete Methods")
	void deleteTest() {
//		CV.deleteObject("cv:id1");
//		JobPosting.deleteObject("saro:Job1");
	}

}
