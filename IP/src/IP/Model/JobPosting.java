package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.jsonldjava.core.RDFDataset.Literal;

import org.apache.jena.base.Sys;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFSubject;

public class JobPosting extends RDFObject {
	
	private static final String ClassType = "saro:JobPosting";
	public static final String prefix = "saro:";
	private static final String schemaPrefix = "schema:";
//	private String URI;
//	private String ID;
//	private String Label;
//	//Seniority Level in the comment section of JobPosting
//	private String Comment;
	private String jobDescription;
	private String contractType;
	private String sector;
	private String occupation;
	private String listingOrganization;
	private String hiringOrganization;
	private String jobLocation;
	private String startDate;
	private String endDate;
	private String seniorityLevel;
	//Required Skills URIs
	private List<String> skillReqURIs;
	//Required Skills
	private List<Skill> skillReq;
	//match with courses
	private List<Course> coursesReq;
	//match with work experience
	private List<WorkHistory> workExperienceReq;
	//match with education and courses?
	private List<Education> educationReq;
	
	//Might have to change according to QualiChain ontology model
	
	//TODO: Add constructors for different cases if found necessary
	public JobPosting() {
		super(ClassType, prefix);
		skillReqURIs = new ArrayList<String>();
		skillReq = new ArrayList<Skill>();
		coursesReq = new ArrayList<Course>();
		workExperienceReq = new ArrayList<WorkHistory>();
		educationReq = new ArrayList<Education>();
	}
	
	public JobPosting(String id, String label,String comment, String jobDescription, String contractType, String sector, String occupation,
			String listingOrg, String hiringOrg, String jobLoc, String startDate, String endDate, String seniorityLevel,
			List<Skill> skillReq, List<Course> capReq, List<WorkHistory> knowReq, List<Education> exptReq) {
		super(ClassType, prefix, id, label, comment);
//		this.ID = id;
//		this.URI = prefix+id;
//		this.Label = label;
//		this.Comment = comment;
		this.jobDescription = jobDescription;
		this.contractType= contractType;
		this.sector = sector;
		this.occupation = occupation;
		this.listingOrganization = listingOrg;
		this.hiringOrganization = hiringOrg;
		this.jobLocation = jobLoc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.seniorityLevel = seniorityLevel;
		
		if(skillReq == null) {
			this.skillReq = new ArrayList<Skill>();
			this.skillReqURIs = new ArrayList<String>();
		}	
		else {
			this.skillReq = skillReq;
			this.skillReqURIs = new ArrayList<String>(skillReq.size());
			for(Skill skill: skillReq) {
				this.skillReqURIs.add(skill.getURI());
			}
		}

		
		if(capReq == null) 
			this.coursesReq = new ArrayList<Course>();
		else
			this.coursesReq = capReq;
		
		if(knowReq == null)
			this.workExperienceReq = new ArrayList<WorkHistory>();
		else
			this.workExperienceReq = knowReq;
		
		if(exptReq == null)
			this.educationReq = new ArrayList<Education>();
		else
			this.educationReq = exptReq;
		
	}
	
    
    public String getJobDescription() {
    	return jobDescription;
    }
    
    public void setJobDescription(String description) {
    	this.jobDescription = description;
    }
    
    public String getContractType() {
    	return contractType;
    }
    
    public void setContractType(String contractType) {
    	this.contractType = contractType;
    }
    
    public String getSector() {
    	return sector;
    }
    
    public void setSector(String sector) {
    	this.sector = sector;
    }
    
    public String getOccupation() {
    	return occupation;
    }
    
    public void setOccupation(String occupation) {
    	this.occupation = occupation;
    }
    
    public String getListingOrg() {
    	return listingOrganization;
    }
    
    public void setListingOrg(String listingOrg) {
    	this.listingOrganization = listingOrg;
    }
    
    public String getHiringOrg() {
    	return hiringOrganization;
    }
    
    public void setHiringOrg(String hiringOrg) {
    	this.hiringOrganization = hiringOrg;
    }
    
    public String getjobLocation() {
    	return jobLocation;
    }
    
    public void setjobLocation(String jobLoc) {
    	this.jobLocation = jobLoc;
    }
    
    public String getStartDate() {
    	return startDate;
    }
    
    public void setStartDate(String startDate) {
    	this.startDate = startDate;
    }
    
    public String getEndDate() {
    	return endDate;
    }
    
    public void setEndDate(String endDate) {
    	this.endDate = endDate;
    }
    
    public String getseniorityLevel() {
    	return seniorityLevel;
    }
    
    public void setseniorityLevel(String seniorityLevel) {
    	this.seniorityLevel = seniorityLevel;
    }
    
    public List<Skill> getSkillReq(){
    	return skillReq;
    }
    
    public List<String> getSkillReqURIs(){
    	return skillReqURIs;
    }
    
    public void addSkillReq(String URI) {
        if(!URI.startsWith("cv:") && !URI.startsWith("<http"))
            URI = "<" + URI + ">";

        Skill skill = Skill.getSkill(URI);
        skillReq.add(skill);
        skillReqURIs.add(URI);

    }
    
    public void addSkillReq(Skill skill) {
    	skillReq.add(skill);
    	skillReqURIs.add(skill.getURI());
    }
    
    public void addSkillsReq(List<Skill> skills) {
    	skillReq.addAll(skills);
    	for(Skill skill: skills) {
    		skillReqURIs.add(skill.getURI());
    	}
    }
    
    public List<Course> getCapabilityReq(){
    	return coursesReq;
    }
    
    public void addCapabilityReq(Course capReq) {
    	this.coursesReq.add(capReq);
    }
    
    public List<WorkHistory> getKnowledgeReq(){
    	return workExperienceReq;
    }
    
    public void addKnowledgeReq(WorkHistory KnowledgeReq) {
    	this.workExperienceReq.add(KnowledgeReq);
    }
    
    public List<Education> getExpertiseReq(){
    	return educationReq;
    }
    
    public void addExpertiseReq(Education expertiseReq) {
    	this.educationReq.add(expertiseReq);
    }
    
    
    
    public void Save() throws Exception {
    	
    	super.save();
    	Triple triple;
        
        if(jobDescription != null) {
            triple = new Triple(getURI(), "saro:describes", jobDescription);
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(contractType != null) {
            triple = new Triple(getURI(), schemaPrefix +  "employmentType", contractType);
            SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(sector != null) {
        	triple = new Triple(getURI(), "saro:advertisedIn", sector);
            SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(occupation != null) {
        	triple = new Triple(getURI(), schemaPrefix + "occupationalCategory", occupation);
    		SparqlEndPoint.insertPropertyValue(triple);
    	}
        
        if(listingOrganization != null) {
        	//Not sure if it should be an object or a simple value
//        	triple = new Triple(getURI(), schemaPrefix + "listingOrganization", schemaPrefix + ListingOrganization);
//    		SparqlEndPoint.insertTriple(triple);
    		triple = new Triple(getURI(), schemaPrefix + "listingOrganization", listingOrganization);
    		SparqlEndPoint.insertPropertyValue(triple);
    	}
        
        if(hiringOrganization != null) {
        	//Not sure if it should be an object or a simple value
//        	triple = new Triple(getURI(), schemaPrefix + "hiringOrganization", schemaPrefix +  hiringOrganization);
//    		SparqlEndPoint.insertTriple(triple);
    		triple = new Triple(getURI(), schemaPrefix + "hiringOrganization", hiringOrganization);
    		SparqlEndPoint.insertPropertyValue(triple);
    	}
        
        if(jobLocation != null) {
        	triple = new Triple(getURI(), schemaPrefix + "jobLocation",  jobLocation);
    		SparqlEndPoint.insertPropertyValue(triple);
    	}        
        
        if(startDate != null) {
        	triple = new Triple(getURI(), prefix + "startDate", startDate);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(endDate != null) {
        	triple = new Triple(getURI(), prefix + "endDate", endDate);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(seniorityLevel != null) {
        	triple = new Triple(getURI(), prefix + "level", seniorityLevel);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(Skill skill : skillReq) {

			String label=skill.getLabel();
            if (skill.getURI() == null){
				if (RDFObject.exists(label))
					skill = Skill.getSkillByLabel(label);
				else
					skill.Save(); 
			}
			
        	triple = new Triple(getURI(), "saro:needsSkill", skill.getURI());
        	SparqlEndPoint.insertTriple(triple);
        }
        
        for(Course capability : coursesReq) {
        	triple = new Triple(getURI(), "saro:requiresCapability", capability.getURI());
        	SparqlEndPoint.insertTriple(triple);
        }
        
        for(WorkHistory knowledge : workExperienceReq) {
        	triple = new Triple(getURI(), "saro:requiresKnowledge", knowledge.getURI());
        	SparqlEndPoint.insertTriple(triple);
        }
        
        for(Education expertise : educationReq) {
        	triple = new Triple(getURI(), "saro:requiresExpertise", expertise.getURI());
        	SparqlEndPoint.insertTriple(triple);
        }

    }
    

	public List<String> getJobSkillReqLabels(){
		List<String> skillLabels = new ArrayList<String>();
		Skill temp = null;
		for(Skill skill : skillReq) {
			temp = Skill.getSkill(skill.getURI());
			skillLabels.add(temp.getLabel());
		}
		return skillLabels;
	}
	
    public static List<JobPosting> getJobPostings(){
        String SparqlJsonResults = SparqlEndPoint.getInstances(JobPosting.ClassType);
        return ParseResponse(SparqlJsonResults);
    }

    //TODO: Analyze and fix this method, returning odd results
    public static JobPosting getJobPosting(String URI){
    	String uri = URI;
        if (!uri.startsWith(JobPosting.prefix) && !uri.startsWith("<http")){
        	if(uri.startsWith("http"))
        		uri = "<" + uri + ">";
        	else
        		uri = JobPosting.prefix+URI;
        }
        String properties = SparqlEndPoint.getAllProperties(uri);
        JobPosting jp = ParseResponseToJobPost(properties);
        jp.setURI(uri);
        return jp;
    }
    

    //Could be a problem if there are multiple job posts with similar labels?
    public static JobPosting getJobPostingByLabel(String label){
    	String SparqlJsonResults = SparqlEndPoint.getInstancesByPartialLabel(ClassType, label);
		String uri = SparqlEndPoint.ParseResponseToURI(SparqlJsonResults);
		return getJobPosting("<" + uri + ">");
    }
    
    public static List<JobPosting> getJobPostingsBySector(String sector){
    	String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(ClassType, "saro:advertisedIn", "saro:" + sector);
    	return ParseResponse(SparqlJsonResults);
    }
    
    public static List<JobPosting> getJobPostingsByListingOrg(String listingOrg){
    	String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(ClassType, "schema:listingOrganization", "schema:" + listingOrg);
    	return ParseResponse(SparqlJsonResults);
    }
    
    public static List<JobPosting> getJobPostingsByHiringOrg(String hiringOrg){
    	String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(ClassType, "schema:hiringOrganization", "schema:" + hiringOrg);
    	return ParseResponse(SparqlJsonResults);
    }
    
    public static List<JobPosting> getJobPostingsByContractType(String contractType){
    	String SparqlJsonResults = SparqlEndPoint.getInstancesByPropertyPartialValue(ClassType, "schema:employmentType", contractType);
    	return ParseResponse(SparqlJsonResults);
    }
    
    public static JobPosting deleteObject(String URI) {
    	JobPosting jp = JobPosting.getJobPosting(URI);
    	SparqlEndPoint.deleteObjectByUri(jp.getURI());
    	return jp;
    }
    
    
    
    private static List<JobPosting> ParseResponse(String SparqlJsonResults){

        List<JobPosting> JPs = new ArrayList<>();  
        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);
        
        while (results.hasNext()) {

            QuerySolution soln = results.nextSolution();
            Resource res= soln.getResource("subject");

            //String ID = String.valueOf(res);            
            String ID =  res.getLocalName(); 
            JobPosting jp = getJobPosting(prefix + ID);

            jp.setID( ID);   
            //cv.setURI(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

            JPs.add(jp);  
        } 
        return JPs; 

    }
    
    private static JobPosting ParseResponseToJobPost(String SparqlJsonResults){

        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);

//        System.out.println("============================================");
        
        JobPosting jp = new JobPosting();

        while (results.hasNext()) {

            QuerySolution soln = results.nextSolution();
            
            //System.out.println("soln: "+soln.toString());

            //String predicate = String.valueOf(soln.getResource("predicate"));   
            //System.out.println("predicate: "+predicate);

            Resource res= soln.getResource("predicate");

            RDFNode Onode = soln.get("object");
            String object="";
            if (Onode.isResource()) {
                object = String.valueOf(soln.getResource("object"));
            }
            else{
                object = String.valueOf(soln.getLiteral("object"));   
            }

            switch (res.getLocalName()) {
                case "type":
                    String type = object;   
//                    System.out.println("type: "+type);
                    break;

                case "label":
                    String label = object;   
//                    System.out.println("label: " + label);
                    jp.setLabel(label);
                    break;

                case "comment":
                	String comment = object;
//                	System.out.println("comment : " + comment);
                	jp.setComment(comment);
                	break;
                	
                case "describes":
                    String description = object;     
                	if(description.contains("#"))
                		description = description.substring(description.indexOf("#") + 1);
//                    System.out.println("description: "+description);
                    jp.setJobDescription(description);
                    break;

                case "employmentType":
                    String empType = object;   
//                    System.out.println("employmentType: " + empType);
                    jp.setContractType(empType);
                    break;

                case "advertisedIn":
                	String sector = object;
                	if(sector.contains("#"))
                		sector = sector.substring(sector.indexOf("#") + 1);
//                	System.out.println("Sector : " + sector);
                	jp.setSector(sector);
                	break;
                	
                case "occupationalCategory":
                	String occupation = object;
//                	System.out.println("Ocupation/Position: " + occupation);
                	jp.setOccupation(occupation);
                	break;
                	
                case "listingOrganization":
                	String listingOrg = object;
//                	System.out.println("Listed by: " + listingOrg);
                	//contains(prefix associated with the triple in question) is probably the best way to do this without potentially
                	//erasing part of the triple if it contains a "/"
                	if(listingOrg.contains("/"))
                			listingOrg = listingOrg.substring(listingOrg.lastIndexOf("/") + 1);
                	jp.setListingOrg(listingOrg);
                	break;
                	
                case "hiringOrganization":
                	String hiringOrg = object;
//                	System.out.println("Hiring to: " + hiringOrg);
                	if(hiringOrg.contains("/"))
                		hiringOrg = hiringOrg.substring(hiringOrg.lastIndexOf("/") + 1);
                	jp.setHiringOrg(hiringOrg);
                	break;

                case "jobLocation":
                	String jobLoc = object;
//                	System.out.println("Job location: " + jobLoc);
                	if(jobLoc.contains("/"))
                		jobLoc = jobLoc.substring(jobLoc.lastIndexOf("/") + 1);
                	jp.setjobLocation(jobLoc);
                	break;

                case "requiresCapability":
                	String reqCapability = object;
//                	System.out.println("Capability requirement: " + reqCapability);
                	jp.addCapabilityReq(Course.getCourse(reqCapability));
                	break;

                case "requiresKnowledge":
                	String reqKnowledge = object;
//                	System.out.println("Knowledge requirement: " + reqKnowledge);
                	jp.addKnowledgeReq(WorkHistory.getWorkHistory(reqKnowledge));
                	break;

                case "requiresExpertise":
                	String reqExpertise = object;
//                	System.out.println("Expertise requirement: " + reqExpertise);
                	jp.addExpertiseReq(Education.getEducation(reqExpertise));
                	break;
                	
                case "needsSkill":
                	String reqSkill = object;
//                	System.out.println("Skill requirement: " + reqSkill);
                	jp.addSkillReq(reqSkill);
                	break;
                	
                case "startDate":
                	String startDate = object;
                	jp.setStartDate(startDate);
                	break;
                
                case "endDate":
                	String endDate = object;
                	jp.setEndDate(endDate);
                	break;
                	
                case "level": 
                	String level = object;
                	jp.setseniorityLevel(level);
                	break;
                	
                default:
                    break;
            }
            
            //String ID = String.valueOf(soln.getResource("predicate"));
            //System.out.println(ID);   
            //cv.setId(ID);   
            //cv.setId(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

        } 
        return jp; 

    }
}
