package IP.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class SparqlQualiChain {
	
	private CV curCV;
	private Person curPerson;
	private JobPosting curJobPosting;
	private Skill curSkill;
	
	public SparqlQualiChain() {
		curCV = null;
		curPerson = null;
		curJobPosting = null;
		curSkill = null;
	}	
	
	//---------------------------------------Methods related to CVs-------------------------------------------------
	public static CV createCv(String cvId, String cvLabel,String comment, String aboutPerson, String cvTitle,
			String otherInfo) {
		
		CV cv = new CV();
		cv.setId(cvId);
		cv.setURI(cvId);
		cv.setLabel(cvLabel);
		cv.setComment(comment);
		cv.setPersonURI(aboutPerson);
		cv.setTitle(cvTitle);
		cv.setDescription(otherInfo);
		cv.Save();
		System.out.println("CV Created Successfully: " + cvLabel);
		return cv;
	}
	
	public static CV addWorkHistory(String cvURI, String workHistory) {
		
		CV cv = CV.getCV(cvURI);
		cv.addWorkHistory(workHistory);
		cv.Save();
		System.out.println("CV Work History Added Successfully: " + workHistory);
		
		return cv;
	}
	
	public static CV addEducation(String cvURI, String education) {
		
		CV cv = CV.getCV(cvURI);
		cv.addEducation(education);
		cv.Save();
		System.out.println("CV Education Added Successfully: " + education);
		
		return cv;
	}
	
	public static CV addCourse(String cvURI, String course) {
		
		CV cv = CV.getCV(cvURI);
		cv.addCourse(course);
		cv.Save();
		System.out.println("CV Course Added Successfully: " + course);
		
		return cv;
	}
	
	public static CV addSkill(String cvURI, String skillURI) {
		
		CV cv = CV.getCV(cvURI);
		cv.addSkill(skillURI);
		cv.Save();
		System.out.println("CV Skill Added Successfully: " + skillURI);
		
		return cv;
	}
	
	//---------------------------------------Methods related to Persons---------------------------------------------
	
	public static Person createPerson(String id, String label, String comment, String gender, boolean driversLicense,
			String cvURI) {
		Person person = new Person();
		person.setId(id);
		person.setURI(id);
		person.setLabel(label);
		person.setComment(comment);
		person.setGender(gender);
		person.setDriversLicense(driversLicense);
		person.setCVURI(cvURI);
		
		person.Save();
		System.out.println("Person Created Successfully: " + label);
		
		return person;
	}
	
	//qc:field qc:competenceArea
	public static Person addCompetenceArea(String URI, String competenceArea, String areaDescription) {
		
		Person person = Person.getPerson(URI);

		person.setCompetenceArea(competenceArea);
		person.setCompetenceAreaDescription(areaDescription);
		person.Save();
		System.out.println("Person Competence Area Created Successfully: " + areaDescription);
		
		return person;
		
	}
	
	public static Person addQualification(String URI, String qualification) {
		
		Person person = Person.getPerson(URI);
		
		person.addQualification(qualification);
		person.Save();
		System.out.println("Person Qualification Created Successfully: " + qualification);
		
		return person;
		
	}
	
	public static Person addExperience(String URI, String experience) {
		
		Person person = Person.getPerson(URI);
		
		person.addExperience(experience);
		person.Save();
		System.out.println("Person Experience Created Successfully: " + experience);
		
		return person;
		
	}
	
	public static Person addMembership(String URI, String membership) {
		
		Person person = Person.getPerson(URI);
		
		person.addMembership(membership);
		person.Save();
		System.out.println("Person Membership Created Successfully: " + membership);
		
		return person;
		
	}
	
	public static Person addPublication(String URI, String publication) {
		
		Person person = Person.getPerson(URI);
		
		person.addPublication(publication);
		person.Save();
		System.out.println("Person Publication Created Successfully: " + publication);
		
		return person;
		
	}
	
	//--------------------------------------Methods related to Skills-----------------------------------------------
	
	public static Skill createSkill(String skillId, String label, String comment, String priority, String proficiency) {
		Skill skill = new Skill();
		skill.setId(skillId);
		skill.setURI(skillId);
		skill.setLabel(label);
		skill.setComment(comment);
		skill.setPriorityLevel(priority);
		skill.setProficiencyLevel(proficiency);
		
		skill.Save();
		System.out.println("Skill Created Successfully: " + label);
		
		return skill;
	}
	
	//------------------------------------Methods related to Job Posting--------------------------------------------
	public static JobPosting createJobPost(String id, String label, String comment, String description, String contractType,
			String sector, String occupation, String listingOrg, String hiringOrg, String jobLocation, List<String> skills, List<String> capabilities,
			List<String> knowledgeList, List<String> expertiseList) {
		
		JobPosting jobPost = new JobPosting();
		jobPost.setId(id);
		jobPost.setURI(id);
		jobPost.setLabel(label);
		jobPost.setComment(comment);
		jobPost.setJobDescription(description);
		jobPost.setContractType(contractType);
		jobPost.setSector(sector);
		jobPost.setOccupation(occupation);
		jobPost.setListingOrg(listingOrg);
		jobPost.setHiringOrg(hiringOrg);
		jobPost.setJobLocation(jobLocation);
		if(skills != null) {
			for(String skill : skills) {
				jobPost.addSkillReq(skill);
			}
		}
		
		if(capabilities != null) {
			for(String capability : capabilities) {
				jobPost.addCapabilityReq(capability);
			}
		}
		
		if(knowledgeList != null) {
			for(String knowledge : knowledgeList) {
				jobPost.addKnowledgeReq(knowledge);
			}
		}
		
		if(expertiseList != null) {
			for(String expetise : expertiseList) {
				jobPost.addExpertiseReq(expetise);
			}
		}
		
		jobPost.Save();
		System.out.println("JobPosting Created Successfully: " + label);
		
		
		return jobPost;
	}	
	
	public static JobPosting addSkillReq(String URI, String skillURI) {
		JobPosting jobPost = JobPosting.getJobPosting(URI);
		jobPost.addSkillReq(skillURI);
		jobPost.Save();
		System.out.println("JobPosting Skill Requirement Created Successfully: " + skillURI);
		return jobPost;
	}
	
	public static JobPosting addCapabilityReq(String URI, String requirement) {
		JobPosting jobPost = JobPosting.getJobPosting(URI);
		jobPost.addCapabilityReq(requirement);
		jobPost.Save();
		System.out.println("JobPosting Capability Requirement Created Successfully: " + requirement);
		
		return jobPost;
	}
	
	public static JobPosting addKnowledgeReq(String URI, String requirement) {
		JobPosting jobPost = JobPosting.getJobPosting(URI);
		jobPost.addKnowledgeReq(requirement);
		jobPost.Save();
		System.out.println("JobPosting Knowledge Requirement Created Successfully: " + requirement);
		
		return jobPost;
	}

	public static JobPosting addExpertiseReq(String URI, String requirement) {
		JobPosting jobPost = JobPosting.getJobPosting(URI);
		jobPost.addExpertiseReq(requirement);
		jobPost.Save();
		System.out.println("JobPosting Expertise Requirement Created Successfully: " + requirement);
		
		return jobPost;
	}
	
	//Recieve triples and upload directly
	
	public static void addTriplesFromFile(String path) {
		
		StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(path), StandardCharsets.UTF_8)) 
	    {
	        stream.forEach(s -> contentBuilder.append(s).append("\n"));
	    }
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
//	    System.out.println(contentBuilder.toString());
	    
	    String endResult = contentBuilder.toString();
	    
	    SparqlEndPoint.insertTriple(endResult);
	}
	 
}
