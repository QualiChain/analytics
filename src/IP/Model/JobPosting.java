package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Resource;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFSubject;

public class JobPosting {
	
	private static final String ClassType = "saro:JobPosting";
	public static final String prefix = "saro:";
	private static final String schemaPrefix = "schema:";
	private String URI;
	private String ID;
	private String Label;
	//Seniority Level in the comment section of JobPosting
	private String Comment;
	private String JobDescription;
	private String ContractType;
	private String Sector;
	private String Occupation;
	private String ListingOrganization;
	private String HiringOrganization;
	private String JobLocation;
	//Required Skills URIs
	private List<String> SkillReqURIs;
	//Required Skills
	private List<Skill> SkillReq;
	//match with courses
	private List<String> CapabilityReq;
	//match with work experience
	private List<String> KnowledgeReq;
	//match with education and courses?
	private List<String> ExpertiseReq;
	
	//Might have to change according to QualiChain ontology model
	
	
	public JobPosting() {
		SkillReqURIs = new ArrayList<String>();
		SkillReq = new ArrayList<Skill>();
		CapabilityReq = new ArrayList<String>();
		KnowledgeReq = new ArrayList<String>();
		ExpertiseReq = new ArrayList<String>();
	}
	
	 
    @RDFSubject
    public String getUri()	{   
        // must return absolute URI
        return URI;
    }
    
    //unnecessary if setId is used to set the uri as well
    public void setURI(String uri) {
    	if(uri.startsWith(prefix) || uri.startsWith("<"))
    		this.URI = uri;
    	else 
    		this.URI = prefix + uri;
    }

    @RDFSubject(prefix=prefix)
    public String getId()	{ 
        // resource URI will be constructed by concatenation of the prefix and returned value
        return ID;
    }
    public void setId(String id) {  
         this.ID = id;
         this.URI = prefix + id;
    }

    @RDF("rdfs:label")
    public String getLabel()	{ 
        // this property will be represented as an RDF statement with the foaf:name predicate
        return Label;
    }
    
    public void setLabel(String label) {   
        this.Label = label;  
    }
    
    public String getComment() {
    	return Comment;
    }
    
    public void setComment(String comment) {
    	this.Comment = comment;
    }
    
    public String getJobDescription() {
    	return JobDescription;
    }
    
    public void setJobDescription(String description) {
    	this.JobDescription = description;
    }
    
    public String getContractType() {
    	return ContractType;
    }
    
    public void setContractType(String contractType) {
    	this.ContractType = contractType;
    }
    
    public String getSector() {
    	return Sector;
    }
    
    public void setSector(String sector) {
    	this.Sector = sector;
    }
    
    public String getOccupation() {
    	return Occupation;
    }
    
    public void setOccupation(String occupation) {
    	this.Occupation = occupation;
    }
    
    public String getListingOrg() {
    	return ListingOrganization;
    }
    
    public void setListingOrg(String listingOrg) {
    	this.ListingOrganization = listingOrg;
    }
    
    public String getHiringOrg() {
    	return HiringOrganization;
    }
    
    public void setHiringOrg(String hiringOrg) {
    	this.HiringOrganization = hiringOrg;
    }
    
    public String getJobLocation() {
    	return JobLocation;
    }
    
    public void setJobLocation(String jobLoc) {
    	this.JobLocation = jobLoc;
    }
    
    public List<Skill> getSkillReq(){
    	return SkillReq;
    }
    
    public List<String> getSkillReqURIs(){
    	return SkillReqURIs;
    }
    
    public void addSkillReq(String URI) {
        if(!URI.startsWith("cv:") && !URI.startsWith("<http"))
            URI = "<" + URI + ">";

        Skill skill = Skill.getSkill(URI);
        SkillReq.add(skill);
        SkillReqURIs.add(URI);

    }
    
    public void addSkillReq(Skill skill) {
    	SkillReq.add(skill);
    	SkillReqURIs.add(skill.getUri());
    }
    
    public List<String> getCapabilityReq(){
    	return CapabilityReq;
    }
    
    public void addCapabilityReq(String capReq) {
    	this.CapabilityReq.add(capReq);
    }
    
    public List<String> getKnowledgeReq(){
    	return KnowledgeReq;
    }
    
    public void addKnowledgeReq(String KnowledgeReq) {
    	this.KnowledgeReq.add(KnowledgeReq);
    }
    
    public List<String> getExpertiseReq(){
    	return ExpertiseReq;
    }
    
    public void addExpertiseReq(String expertiseReq) {
    	this.ExpertiseReq.add(expertiseReq);
    }
    
    
    
    public void Save() {
    	Triple triple = new Triple(URI, "rdf:type", ClassType);
        SparqlEndPoint.insertTriple(triple);

        if(Label != null) {
        	triple = new Triple(URI, "rdfs:label", Label);
            SparqlEndPoint.insertPropertyValue(triple);	
        }
		
        if(Comment != null) {
        	triple = new Triple(URI, "rdfs:comment", Comment);
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(JobDescription != null) {
            triple = new Triple(URI, "saro:describes", JobDescription);
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(ContractType != null) {
            triple = new Triple(URI, schemaPrefix +  "employmentType", ContractType);
            SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(Sector != null) {
        	triple = new Triple(URI, "saro:advertisedIn", Sector);
            SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(Occupation != null) {
        	triple = new Triple(URI, schemaPrefix + "occupationalCategory", Occupation);
    		SparqlEndPoint.insertPropertyValue(triple);
    	}
        
        if(ListingOrganization != null) {
        	triple = new Triple(URI, schemaPrefix + "listingOrganization", schemaPrefix + ListingOrganization);
    		SparqlEndPoint.insertTriple(triple);
    	}
        
        if(HiringOrganization != null) {
        	triple = new Triple(URI, schemaPrefix + "hiringOrganization", schemaPrefix +  HiringOrganization);
    		SparqlEndPoint.insertTriple(triple);
    	}
        
        if(JobLocation != null) {
        	triple = new Triple(URI, schemaPrefix + "jobLocation", schemaPrefix + JobLocation);
    		SparqlEndPoint.insertTriple(triple);
    	}        
        
        for(Skill skill : SkillReq) {
            if (skill.getUri() == null){       
                skill = Skill.getSkillByLabel(skill.getLabel());
            }
            
            System.out.println(skill.getUri());
        	triple = new Triple(URI, "saro:needsSkill", skill.getUri());
        	SparqlEndPoint.insertTriple(triple);
        }
        
        for(String capability : CapabilityReq) {
        	triple = new Triple(URI, "saro:requiresCapability", capability);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String knowledge : KnowledgeReq) {
        	triple = new Triple(URI, "saro:requiresKnowledge", knowledge);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String expertise : ExpertiseReq) {
        	triple = new Triple(URI, "saro:requiresExpertise", expertise);
        	SparqlEndPoint.insertPropertyValue(triple);
        }

    }
    

	public List<String> getJobSkillReqLabels(){
		List<String> skillLabels = new ArrayList<String>();
		Skill temp = null;
		for(Skill skill : SkillReq) {
			temp = Skill.getSkill(skill.getUri());
			skillLabels.add(temp.getLabel());
		}
		return skillLabels;
	}
	
    public static List<JobPosting> getJobPostings(){
        String SparqlJsonResults = SparqlEndPoint.getInstances(JobPosting.ClassType);
        return ParseResponse(SparqlJsonResults);
    }

    public static JobPosting getJobPosting(String URI){
    
        if (!(URI.startsWith(JobPosting.prefix))){
            URI = JobPosting.prefix+URI;
        }
        
        String properties = SparqlEndPoint.getAllProperties(URI);
        //System.out.println(properties);
        JobPosting jp = ParseResponseToJobPost(properties);
        jp.setURI(URI);
        return jp;
    }
    
    //TODO:
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
            jp.setId( ID);   
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
            
//            System.out.println("soln: "+soln.toString());

            //String predicate = String.valueOf(soln.getResource("predicate"));   
            //System.out.println("predicate: "+predicate);

            Resource res= soln.getResource("predicate");

            switch (res.getLocalName()) {
                case "type":
                    String type = String.valueOf(soln.getResource("object"));   
//                    System.out.println("type: "+type);
                    break;

                case "label":
                    String label = String.valueOf(soln.getLiteral("object"));   
//                    System.out.println("label: " + label);
                    jp.setLabel(label);
                    break;

                case "comment":
                	String comment = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("comment : " + comment);
                	jp.setComment(comment);
                	break;
                	
                case "describes":
                    String description = String.valueOf(soln.getLiteral("object"));     
                	if(description.contains("#"))
                		description = description.substring(description.indexOf("#") + 1);
//                    System.out.println("description: "+description);
                    jp.setJobDescription(description);
                    break;

                case "employmentType":
                    String empType = String.valueOf(soln.getLiteral("object"));   
//                    System.out.println("employmentType: " + empType);
                    jp.setContractType(empType);
                    break;

                case "advertisedIn":
                	String sector = String.valueOf(soln.getLiteral("object"));       
                	if(sector.contains("#"))
                		sector = sector.substring(sector.indexOf("#") + 1);
//                	System.out.println("Sector : " + sector);
                	jp.setSector(sector);
                	break;
                	
                case "occupationalCategory":
                	String occupation = String.valueOf(soln.getLiteral("object"));
//                	System.out.println("Ocupation/Position: " + occupation);
                	jp.setOccupation(occupation);
                	break;
                	
                case "listingOrganization":
                	String listingOrg = String.valueOf(soln.getResource("object"));
//                	System.out.println("Listed by: " + listingOrg);
                	//contains(prefix associated with the triple in question) is probably the best way to do this without potentially
                	//erasing part of the triple if it contains a "/"
                	if(listingOrg.contains("/"))
                			listingOrg = listingOrg.substring(listingOrg.lastIndexOf("/") + 1);
                	jp.setListingOrg(listingOrg);
                	break;
                	
                case "hiringOrganization":
                	String hiringOrg = String.valueOf(soln.getResource("object"));
//                	System.out.println("Hiring to: " + hiringOrg);
                	if(hiringOrg.contains("/"))
                		hiringOrg = hiringOrg.substring(hiringOrg.lastIndexOf("/") + 1);
                	jp.setHiringOrg(hiringOrg);
                	break;

                case "jobLocation":
                	String jobLoc = String.valueOf(soln.getResource("object"));
//                	System.out.println("Job location: " + jobLoc);
                	if(jobLoc.contains("/"))
                		jobLoc = jobLoc.substring(jobLoc.lastIndexOf("/") + 1);
                	jp.setJobLocation(jobLoc);
                	break;

                case "requiresCapability":
                	String reqCapability = String.valueOf(soln.getResource("object"));
//                	System.out.println("Capability requirement: " + reqCapability);
                	jp.addCapabilityReq(reqCapability);
                	break;

                case "requiresKnowledge":
                	String reqKnowledge = String.valueOf(soln.getResource("object"));
//                	System.out.println("Knowledge requirement: " + reqKnowledge);
                	jp.addKnowledgeReq(reqKnowledge);
                	break;

                case "requiresExpertise":
                	String reqExpertise = String.valueOf(soln.getResource("object"));
//                	System.out.println("Expertise requirement: " + reqExpertise);
                	jp.addExpertiseReq(reqExpertise);
                	break;
                	
                case "needsSkill":
                	String reqSkill = String.valueOf(soln.getResource("object"));
//                	System.out.println("Skill requirement: " + reqSkill);
                	jp.addSkillReq(reqSkill);
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
