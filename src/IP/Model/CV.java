package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Resource;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFBean;
import org.cyberborean.rdfbeans.annotations.RDFNamespaces;
import org.cyberborean.rdfbeans.annotations.RDFSubject;

@RDFNamespaces(
{      
    "rdfs = http://www.w3.org/2000/01/rdf-schema",
    "qc = http://www.qualichain.eu/ontology/",
    "cv = http://rdfs.org/resume-rdf/cv.rdfs/" 
})

@RDFBean("cv:CV")
public class CV  {
	
    private String URI;
    private String ID;
    private String Label;
    private String Comment;
    private static String ClassType ="cv:CV";     
    private static String prefix ="cv:";     
    private String Title;
    private String PersonURI;
    private String Description;
    private String targetSector;
    private String targetRole;
    private String otherInfo;
    private String expectedSalary;
    private String salaryCurrency;
    private List<String> WorkHistory;
    private List<String> Education;
    private List<String> Courses;
    private List<Skill> Skills;
    private List<String> SkillURIs;

    public CV() {
    	WorkHistory = new ArrayList<>();
    	Education = new ArrayList<>();
    	Courses = new ArrayList<>();
    	Skills = new ArrayList<>();
    	SkillURIs = new ArrayList<>();
    }

    public String getExpectedSalary()	{   
        return expectedSalary;
    }

    public String getPersonName()	{   
        return "Noname";
    }

    public String getRole()	{   
        return targetRole;
    }

    @RDFSubject
    public String getURI()	{   
        return URI;
    }
    
    public void setURI(String uri) {
    	if(uri.startsWith(prefix) || uri.startsWith("<"))
    		this.URI = uri;
    	else
    		this.URI = prefix + uri;
    }

    @RDFSubject(prefix = "cv:")
    public String getId()	{ 
        return ID;
    }
    public void setId(String id) {  
         this.ID = id;
    }

    @RDF("rdfs:label")
    public String getLabel()	{ 
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

    
    public String getTitle() {
    	return Title;
    }
    
    public void setTitle(String title) {
    	this.Title = title;
    }
    
    public String getPersonURI() {
    	return PersonURI;
    }
    
    public void setPersonURI(String personURI) {
    	this.PersonURI = personURI;
    }
    
    public String getDescription() {
    	return Description;
    }
    
    public void setDescription(String description) {
    	this.Description = description;
    }
    
    public List<String> getWorkHistory()	{
    	return this.WorkHistory;
    }
    
    public void addWorkHistory(String workHistory) {
    	this.WorkHistory.add(workHistory);
    }
    
    public List<String> getEducation()	{
    	return this.Education;
    }
    
    public void addEducation(String education) {
    	this.Education.add(education);
    }
    
    public List<String> getCourses()	{
    	return this.Courses;
    }
    
    public void addCourse(String course) {
    	this.Courses.add(course);
    }
    
    public List<Skill> getSkills()	{
    	return Skills;
    }
    
    public List<String> getSkillURIs(){
    	return SkillURIs;
    }
    
    //add Skill by uri or ID
    public void addSkill(String skill) {
    	if(!skill.startsWith(prefix)) {
    		this.Skills.add(Skill.getSkill(prefix + skill));
    		this.SkillURIs.add(prefix + skill);
    	}
    		
    	else {
    		this.Skills.add(Skill.getSkill(skill));
    		this.SkillURIs.add(skill);
    	}
    } 
    
    //add Skill by object
    public void addSkill(Skill skill) {
    	this.Skills.add(skill);
    	this.SkillURIs.add(skill.getUri());
    }
    
    
    public boolean hasSkill(String skillLabel){
    	for(Skill curSkill : Skills) {
    		String label = curSkill.getLabel();
    		
    		if(label.equalsIgnoreCase(skillLabel)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //TODO: Generalize all other competences to skills and then specify what each skill is?
    public boolean hasWorkExperience ( String workExperience) {
    	for(String job : WorkHistory) {
    		//not sure I should keep the IgnoreCase since it's a URI 
    		if(job.equalsIgnoreCase(workExperience)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean hasEducation ( String education) {
    	for(String certificate : Education) {
    		//not sure I should keep the IgnoreCase since it's a URI 
    		if(certificate.equalsIgnoreCase(education)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean hasCourse ( String course) {
    	for(String certificate : Courses) {
    		//not sure I should keep the IgnoreCase since it's a URI 
    		if(certificate.equalsIgnoreCase(course)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public void Save() {

		//Insert CV
        Triple triple = new Triple(URI, "rdf:type", ClassType);
        SparqlEndPoint.insertTriple(triple);
        
        if(Label != null) {
	        //Insert CV label
			triple = new Triple(URI, "rdfs:label", Label);
	        SparqlEndPoint.insertPropertyValue(triple);
//	        triple = new Triple(URI, "foaf:name", Label);
//	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(Comment != null) {
	        //Insert CV Comment
			triple = new Triple(URI, "rdfs:comment", Comment);
			SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(Title != null) {
	        //Insert CV title
	        triple = new Triple(URI, "cv:cvTitle", Title);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(PersonURI != null) {
	        //Insert Person CV association
	        triple = new Triple(URI, "cv:aboutPerson", "qc:" + PersonURI);
	        SparqlEndPoint.insertTriple(triple);
        }
        
        if(Description != null) {
	        //Insert CV description
	        triple = new Triple(URI, "cv:hasDescription", Description);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(targetSector != null) {
	        triple = new Triple(URI, "cv:targetJobDescription", targetSector);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(expectedSalary != null) {
	        triple = new Triple(URI + "_SALARY", "rdf:type", "cv:Target");
	        SparqlEndPoint.insertTriple(triple);
	        
	        triple = new Triple(URI, "cv:hasTarget", URI + "_SALARY");
	        SparqlEndPoint.insertTriple(triple);
	        
	        triple = new Triple(URI + "_SALARY", "cv:targetSalary", expectedSalary);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(salaryCurrency != null && expectedSalary != null) {
	        triple = new Triple(URI + "_SALARY", "cv:targetSalaryCurrency", salaryCurrency);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(otherInfo != null) {
	        triple = new Triple(URI , "cv:hasOtherInfo", otherInfo);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        //Insert CV work history list
        for(String workHistory : WorkHistory) {
        	triple = new Triple(URI, "cv:hasWorkHistory", workHistory);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        //Insert CV education list
        for(String education : Education) {
        	triple = new Triple(URI, "cv:hasEducation", education);
        	SparqlEndPoint.insertPropertyValue(triple);
        	
        }
        
        //Insert CV courses
        for(String course : Courses) {
        	triple = new Triple(URI, "cv:hasCourse", course);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        //Insert CV skills
        for(Skill skill : Skills) {
        	triple = new Triple(URI, "cv:hasSkill", skill.getUri());
        	SparqlEndPoint.insertTriple(triple);
        }

    }

    public static List<CV> getCVs() {
        String SparqlJsonResults = SparqlEndPoint.getInstances(CV.ClassType);
        return ParseResponse(SparqlJsonResults);
    }

    //TODO:not sure what to do with this yet?
    public static List<CV> getCVs(String SkillURI){
        //if (SparqlEndPoint.existURI(SkillURI)){
            
            Skill skill = Skill.getSkill(SkillURI);
            String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(CV.ClassType,"qc:hasAccomplishment",SkillURI);
            return ParseResponse(SparqlJsonResults);
    
        //}
        //return null;
    }

    public static List<CV> getCVsBySkill(String Skilllabel){
        Skill skill = Skill.getSkillByLabel(Skilllabel);
        
        String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(CV.ClassType, "cv:hasSkill", skill.getUri());
        return ParseResponse(SparqlJsonResults);
    }

    public static CV getCV(String URI){

        if (!(URI.startsWith(CV.prefix))){
            URI = CV.prefix+URI;
        }

        String properties = SparqlEndPoint.getAllProperties(URI);
//        System.out.println(properties);
        CV cv = ParseResponseToCV(properties);
        cv.setURI(URI);
        return cv;
    }
    
    public static CV getCVbyPerson(String name) {
    	Person person = Person.getPersonByName(name);
    	System.out.println(person.getURI());
    	String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(CV.ClassType, "cv:aboutPerson", person.getURI() );
    	return ParseResponse(SparqlJsonResults).get(0);
    }

    
    private static List<CV> ParseResponse(String SparqlJsonResults){

        List<CV> CVs = new ArrayList<>();  
        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);
        
        while (results.hasNext()) {

            QuerySolution soln = results.nextSolution();
            Resource res= soln.getResource("subject");

            //String ID = String.valueOf(res);            
            String ID =  res.getLocalName();            
            CV cv = getCV(prefix + ID);
            cv.setId( ID);   
            //cv.setURI(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

            CVs.add(cv);  
        } 
        return CVs; 

    }
    
    private static CV ParseResponseToCV(String SparqlJsonResults){

        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);

//        System.out.println("============================================");
        
        CV cv = new CV();

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
                    cv.setLabel(label);
                    break;
                    
                case "cvTitle":
                	String title = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("cvTitle: " + title);
                	cv.setTitle(title);
                	break;
            
                case "aboutPerson":
                	String personURI = String.valueOf(soln.getResource("object"));  
                	if(personURI.contains("#"))
                		personURI = personURI.substring(personURI.indexOf("#") + 1);
//                	System.out.println("aboutPerson: " + personURI);
                	cv.setPersonURI(personURI);
                	break;
                	
                case "hasSkill":
                	String skillURI = String.valueOf(soln.getResource("object"));  
                	if(skillURI.contains("#"))
                		skillURI = skillURI.substring(skillURI.indexOf("#") + 1);
//                	System.out.println("hasSkill: " + skillURI);
                	cv.addSkill(skillURI);
                	break;
                	
                case "hasWorkHistory":
                	String workHistory = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("Work History: " + workHistory);
                	cv.addWorkHistory(workHistory);
                	break;
                	

                case "hasDescription":
                	String description = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("hasDescription: " + description);
                	cv.setDescription(description);
                	break;

                case "comment":
                	String comment = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("comment : " + comment);
                	cv.setComment(comment);
                	break;
                	

                case "hasEducation":
                	String education = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("hasEducation: " + education);
                	cv.addEducation(education);
                	break;
                	

                case "hasCourse":
                	String course = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("hasCourse: " + course);
                	cv.addCourse(course);
                	break;
                	
                default:
                    break;
            }
            
            //String ID = String.valueOf(soln.getResource("predicate"));
            //System.out.println(ID);   
            //cv.setId(ID);   
            //cv.setId(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

        } 
        return cv; 

    }

}
