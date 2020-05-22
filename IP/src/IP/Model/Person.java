package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Resource;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFBean;
import org.cyberborean.rdfbeans.annotations.RDFNamespaces;
import org.cyberborean.rdfbeans.annotations.RDFSubject;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.gson.JsonObject;

@RDFNamespaces(
{
    "rdfs = http://www.w3.org/2000/01/rdf-schema",
    "qc = http://www.qualichain.eu/ontology/",
    "cv = http://rdfs.org/resume-rdf/cv.rdfs/" 
})

@RDFBean("qc:Person")
public class Person extends RDFObject {
    private static final String ClassType ="qc:Person";  
    public static final String prefix = "qc:";
    private String Name;
    private String SurName;
    private String Gender;
    private String PhoneNumber;
    private String Email;
    //Like a linkedin page or a blog for reference work for example
    private String PersonalPage;
    private String Nationality;
    //Not sure what ontology to use for the address
    private String Address;
    private boolean DriversLicense;
    private String CVURI;
    //Describe what the area of interest is or the area of expertise in a field?
    private String CompetenceArea;
    private String CompetenceAreaDescription;
    private String Role;
    private List<String> Qualifications;
    private List<String> Experiences;
    private List<String> Memberships;
    private List<String> Publications;
    private List<String> Accomplishments;
    private List<Application> JobApplications;
    
    
  //TODO: Add constructors for different cases if found necessary
    public Person() {
    	super(ClassType, prefix);
    	Qualifications = new ArrayList<>();
    	Experiences = new ArrayList<>();
    	Memberships = new ArrayList<>();
    	Publications = new ArrayList<>();
    	Accomplishments = new ArrayList<>();
    	JobApplications = new ArrayList<>();
    }
    
    public Person(String id, String name, String surName, String comment, String gender, String phoneNum,
    		String email, String personalPage, String nationality, String address, boolean driversLicense,
    		String cvUri, String competenceArea, String competenceAreaDesc, String role, List<String> qualif,
    		List<String> exp, List<String> member, List<String> pubs, List<String> accomp) {
    	super(ClassType, prefix, id, name + " " + surName, comment);
    	this.Name = name;
    	this.SurName = surName;
    	this.Gender = gender;
    	this.PhoneNumber = phoneNum;
    	this.Email = email;
    	this.PersonalPage = personalPage;
    	this.Nationality = nationality;
    	this.Address = address;
    	this.DriversLicense = driversLicense;
    	this.CVURI = cvUri;
    	this.CompetenceArea = competenceArea;
    	this.CompetenceAreaDescription = competenceAreaDesc;
    	this.Role = role;
    	this.Qualifications = qualif;
    	if(Qualifications == null)
    		Qualifications = new ArrayList<>();
    	
    	this.Experiences = exp;
    	if(Experiences == null)
    		Experiences = new ArrayList<>();
    	
    	this.Memberships = member;
    	if(Memberships == null)
    		Memberships = new ArrayList<>();
    	
    	this.Publications = pubs;
    	if(Publications == null)
    		Publications = new ArrayList<>();
    	
    	this.Accomplishments = accomp;
    	if(Accomplishments == null)
    		Accomplishments = new ArrayList<>();
    	
    	this.JobApplications = new ArrayList<>();
    }
    
    public String getName() {
    	return Name;
    }
    
    public void setName(String name) {
    	this.Name = name;
    }
    
    public String getSurName() {
    	return SurName;
    }
    
    public void setSurName(String surName) {
    	this.SurName = surName;
    }
    
    public String getGender() {
    	return this.Gender;
    }
    
    public void setGender(String gender) {
    	this.Gender = gender;
    }
    
    public String getAddress() {
    	return this.Address;
    }
    
    public void setAddress(String address) {
    	this.Address = address;
    }
    
    public boolean getDriversLicense() {
    	return this.DriversLicense;
    }
    
    //Should be a boolean value, not string, maybe restricting the String to true or false is easier though
    public void setDriversLicense(boolean driversLicense) {
    	this.DriversLicense = driversLicense;
    }
    
    public String getCVURI() {
    	return CVURI;
    }
    
    public void setCVURI(String CVURI) {
    	this.CVURI = "cv:" + CVURI;
    }
    
    public String getCompetenceArea() {
    	return CompetenceArea;
    }
    
    public void setCompetenceArea(String competenceArea) {
    	this.CompetenceArea = competenceArea;
    }
    
    public String getCompetenceAreaDescription() {
    	return this.CompetenceAreaDescription;
    }
    
    public void setCompetenceAreaDescription(String competenceAreaDescription) {
    	this.CompetenceAreaDescription = competenceAreaDescription;
    }
    
    public String getRole() {
    	return this.Role;
    }
    
    public void setRole(String role) {
    	this.Role = role;
    }
    
    public List<String> getQualifications(){
    	return Qualifications;
    }
    
    public void addQualification(String qualification) {
    	this.Qualifications.add(qualification);
    }
    
    public List<String> getExperiences(){
    	return Experiences;
    }
    
    public void addExperience(String experience) {
    	this.Experiences.add(experience);
    }
    
    public List<String> getMembership(){
    	return Memberships;
    }
    
    public void addMembership(String membership) {
    	this.Memberships.add(membership);
    }
    
    public List<String> getPublications(){
    	return Publications;
    }
    
    public void addPublication(String publication) {
    	this.Publications.add(publication);
    }
    
    public List<String> getAccomplishments(){
    	return Accomplishments;
    }
    
    public void addAccomplishment(String accomplishment) {
    	this.Accomplishments.add(accomplishment);
    }
    
    public List<Application> getApplications(){
    	return JobApplications;
    }
    
    public void addJobApplication(Application app) {
    	this.JobApplications.add(app);
    }
    
	public void Save() throws Exception {
		Triple triple;
		super.save();
        
		//TODO: Double check foaf ontology for first and last name
		//foaf:name is a junction of name surname and title
		//could add title such as MD PHD and so on
		if(Name != null) {
			triple = new Triple(getURI(), "foaf:firstName", Name);
			SparqlEndPoint.insertPropertyValue(triple);
		}
		
		if(SurName != null) {
			triple = new Triple(getURI(), "foaf:lastName", SurName);
			SparqlEndPoint.insertPropertyValue(triple);
		}
		
        if(Gender != null) {
            triple = new Triple(getURI(), "cv:gender", Gender);
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(DriversLicense) {
            triple = new Triple(getURI(), "cv:hasDriversLicense", "true");
            SparqlEndPoint.insertPropertyValue(triple);
        }
        else {
            triple = new Triple(getURI(), "cv:hasDriversLicense", "false");
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(CVURI != null) {
        	triple = new Triple(getURI(), "qc:hasResume", CVURI);
            SparqlEndPoint.insertTriple(triple);	
        }
        
        if(CompetenceArea != null) {
        	triple = new Triple("qc:" + CompetenceArea, "rdf:type", "qc:CompetenceArea");
    		SparqlEndPoint.insertTriple(triple);
    		
    		triple = new Triple(getURI(), "qc:field", "qc:" + CompetenceArea);
            SparqlEndPoint.insertTriple(triple);
        }
        
        if(CompetenceArea != null && CompetenceAreaDescription != null) {
    		triple = new Triple("qc:" + CompetenceArea, "rdfs:label", CompetenceAreaDescription);
    		SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(Nationality != null) {
        	triple = new Triple(getURI(), "cv:hasNationality", Nationality);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String qualification : Qualifications) {
        	triple = new Triple(getURI(), "qc:hasAccomplishment", prefix + qualification);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + qualification, "rdf:type", "qc:Qualification");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String experience : Experiences) {
        	triple = new Triple(getURI(), "qc:hasAccomplishment", prefix + experience);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix +experience, "rdf:type", "qc:Experience");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String membership : Memberships) {
        	triple = new Triple(getURI(), "qc:hasAccomplishment", prefix + membership);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + membership, "rdf:type", "qc:Membership");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String publication : Publications) {
        	triple = new Triple(getURI(), "qc:hasAccomplishment", prefix + publication);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + publication, "rdf:type", "qc:Publication");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(Application app: JobApplications) {
        	triple = new Triple(getURI(), "qc:hasAppliedTo", app.getURI());
        	SparqlEndPoint.insertTriple(triple);
        }
}
	
    public static List<Person> getPersons(){
        String SparqlJsonResults = SparqlEndPoint.getInstances(Person.ClassType);
        return ParseResponse(SparqlJsonResults);
    }

	public static Person getPerson(String URI){
        String uri = URI;
		if (!uri.startsWith(Person.prefix) && !uri.startsWith("<http")){
			if(uri.startsWith("http"))
				uri = "<" + uri + ">";
			else
				uri = Person.prefix+uri;
        }
		
        String properties = SparqlEndPoint.getAllProperties(uri);
        //System.out.println(properties);
        Person person = ParseResponseToPerson(properties);
        person.setURI(uri);
        return person;
    }
	
	//TODO:getPersonsByField should get the persons with the field through the fields label instead or partial match
	//GET THE PERSON JSON RESULTS IN A SINGLE JSON
	public static List<Person> getPersonsByField(String field){
		List<Person> persons = new ArrayList<Person>();
		String SparqlJsonFieldResults = SparqlEndPoint.getInstancesByPartialLabel("qc:CompetenceArea", field);
		List<String> properties = ParseResponseToProperty(SparqlJsonFieldResults);
		List<String> SparqlJsonResults = new ArrayList<String>();
		
		for(String property: properties) {
			SparqlJsonResults.add(SparqlEndPoint.getInstancesByProperty(ClassType, "qc:field","<" + property + ">" ));		
			
		}
		
		InputStream in = null;
		ResultSet results = null;
		for(String person: SparqlJsonResults) {
			in = new ByteArrayInputStream(person.getBytes(StandardCharsets.UTF_8));
	        results = ResultSetFactory.fromJSON(in);
	        while(results.hasNext()) {
	        	QuerySolution soln = results.nextSolution();
	            persons.add(getPerson(String.valueOf(soln.getResource("subject"))));
	        }
		}
//		return ParseResponse(SparqlJsonResults);
		return persons;
	}
	
	public static Person getPersonByName(String name) {
		String SparqlJsonResults = SparqlEndPoint.getInstancesByPartialLabel(ClassType, name);
		String uri = SparqlEndPoint.ParseResponseToURI(SparqlJsonResults);
		return getPerson("<" + uri + ">");
	}
	
	public static Person getPersonByCV(String cvURI) {
		String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(ClassType, "qc:hasResume", cvURI);
		String uri = SparqlEndPoint.ParseResponseToURI(SparqlJsonResults);
		
		return getPerson("<" + uri + ">");
	}
	
	public static Person deleteObject(String URI) {
		Person p = Person.getPerson(URI);
    	SparqlEndPoint.deleteObjectByUri(p.getURI());
    	return p;
    }
	
	
	private static List<String> ParseResponseToProperty(String SparqlJsonResults) {
        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);
        List<String> properties = new ArrayList<String>();
        while(results.hasNext()) {
        	
        	QuerySolution soln = results.nextSolution();
        	
        	properties.add(String.valueOf(soln.getResource("subject")));
        }
        
		return properties;
	}
	
	private static Person ParseResponseToPerson(String SparqlJsonResults){

        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);

//        System.out.println("============================================");
        
        Person person = new Person();

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
                    person.setLabel(label);
                    break;
                    
                case "firstName":
                	String name = String.valueOf(soln.getLiteral("object")); 
                	person.setName(name);
                	break;

                case "lastName":
                	String surName = String.valueOf(soln.getLiteral("object")); 
                	person.setSurName(surName);
                	break;
                	
                case "gender":
                	String gender = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("gender: " + gender);
                	person.setGender(gender);
                	break;
            
                case "hasDriversLicense":
                	String license = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("hasDriversLicense: " + license);
                	if(license.equals("true"))
                		person.setDriversLicense(true);
                	else if(license.equals("false"))
                		person.setDriversLicense(false);	
                	break;
                	
                case "hasResume":
                	String resumeURI = String.valueOf(soln.getResource("object"));  
                	if(resumeURI.contains("#"))
                		resumeURI = resumeURI.substring(resumeURI.indexOf("#") + 1);
//                	System.out.println("hasResume: " + resumeURI);
                	person.setCVURI(resumeURI);
                	break;
                	
                case "field":
                	String competenceArea = String.valueOf(soln.getResource("object"));  
                	if(competenceArea.contains("#"))
                		competenceArea = competenceArea.substring(competenceArea.indexOf("#") + 1);
//                	System.out.println("Competence Area: " + competenceArea);
                	person.setCompetenceArea(competenceArea);
                	break;
                	

                case "hasAccomplishment":
                	String accomplishment = String.valueOf(soln.getResource("object"));  
//                	System.out.println("hasAccomplishment: " + accomplishment);
                	person.addAccomplishment(accomplishment);
                	break;

                case "comment":
                	String comment = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("comment : " + comment);
                	person.setComment(comment);
                	break;
                          	
                default:
                    break;
            }
            
            //String ID = String.valueOf(soln.getResource("predicate"));
            //System.out.println(ID);   
            //cv.setId(ID);   
            //cv.setId(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

        } 
        return person; 

    }


	private static List<Person> ParseResponse(String SparqlJsonResults) {

        List<Person> Persons = new ArrayList<>();  
        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);
        
        while (results.hasNext()) {
        	QuerySolution soln = results.nextSolution();
            Resource res= soln.getResource("subject");

            //String ID = String.valueOf(res);            
            String ID =  res.getLocalName();            
            Person person = getPerson(prefix + ID);
            person.setID( ID);   
            //cv.setURI(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

            Persons.add(person); 
        } 
        return Persons; 
	}
	
	//Maybe add a String defining the type of accomplishment and use it to further define the accomplishment and its details
	private void saveAccomplishment(String accomplishment) {
    	Triple triple = new Triple(getURI(),"qc:hasAccomplishment", accomplishment);
    	SparqlEndPoint.insertPropertyValue(triple);
		
	}

}
