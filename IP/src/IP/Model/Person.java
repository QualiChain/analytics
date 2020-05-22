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
import org.apache.jena.rdf.model.RDFNode;
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
    private String name;
    private String surname;
    private String gender;
    private String phoneNumber;
    private String email;
    //Like a linkedin page or a blog for reference work for example
    private String personalPage;
    private String nationality;
    //Not sure what ontology to use for the address
    private String address;
    private boolean driversLicense;
    private String cvURI;
    //Describe what the area of interest is or the area of expertise in a field?
    private String competenceArea;
    private String competenceAreaDescription;
    private String role;
    private List<String> qualifications;
    private List<String> experiences;
    private List<String> memberships;
    private List<String> publications;
    private List<String> accomplishments;
    private List<Application> jobApplications;
    
    
  //TODO: Add constructors for different cases if found necessary
    public Person() {
    	super(ClassType, prefix);
    	qualifications = new ArrayList<>();
    	experiences = new ArrayList<>();
    	memberships = new ArrayList<>();
    	publications = new ArrayList<>();
    	accomplishments = new ArrayList<>();
    	jobApplications = new ArrayList<>();
    }
    
    public Person(String id, String name, String surname, String comment, String gender, String phoneNum,
    		String email, String personalPage, String nationality, String address, boolean driversLicense,
    		String cvUri, String competenceArea, String competenceAreaDesc, String role, List<String> qualif,
    		List<String> exp, List<String> member, List<String> pubs, List<String> accomp) {
    	super(ClassType, prefix, id, name + " " + surname, comment);
    	this.name = name;
    	this.surname = surname;
    	this.gender = gender;
    	this.phoneNumber = phoneNum;
    	this.email = email;
    	this.personalPage = personalPage;
    	this.nationality = nationality;
    	this.address = address;
    	this.driversLicense = driversLicense;
    	this.cvURI = cvUri;
    	this.competenceArea = competenceArea;
    	this.competenceAreaDescription = competenceAreaDesc;
    	this.role = role;
    	this.qualifications = qualif;
    	if(qualifications == null)
    		qualifications = new ArrayList<>();
    	
    	this.experiences = exp;
    	if(experiences == null)
    		experiences = new ArrayList<>();
    	
    	this.memberships = member;
    	if(memberships == null)
    		memberships = new ArrayList<>();
    	
    	this.publications = pubs;
    	if(publications == null)
    		publications = new ArrayList<>();
    	
    	this.accomplishments = accomp;
    	if(accomplishments == null)
    		accomplishments = new ArrayList<>();
    	
    	this.jobApplications = new ArrayList<>();
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getsurname() {
    	return surname;
    }
    
    public void setsurname(String surname) {
    	this.surname = surname;
    }
    
    public String getGender() {
    	return this.gender;
    }
    
    public void setGender(String gender) {
    	this.gender = gender;
    }
    
    public String getAddress() {
    	return this.address;
    }
    
    public void setAddress(String address) {
    	this.address = address;
    }
    
    public boolean getDriversLicense() {
    	return this.driversLicense;
    }
    
    //Should be a boolean value, not string, maybe restricting the String to true or false is easier though
    public void setDriversLicense(boolean driversLicense) {
    	this.driversLicense = driversLicense;
    }
    
    public String getCVURI() {
    	return cvURI;
    }
    
    public void setCVURI(String CVURI) {
    	this.cvURI = "cv:" + CVURI;
    }
    
    public String getCompetenceArea() {
    	return competenceArea;
    }
    
    public void setCompetenceArea(String competenceArea) {
    	this.competenceArea = competenceArea;
    }
    
    public String getCompetenceAreaDescription() {
    	return this.competenceAreaDescription;
    }
    
    public void setCompetenceAreaDescription(String competenceAreaDescription) {
    	this.competenceAreaDescription = competenceAreaDescription;
    }
    
    public String getRole() {
    	return this.role;
    }
    
    public void setRole(String role) {
    	this.role = role;
    }
    
    public List<String> getQualifications(){
    	return qualifications;
    }
    
    public void addQualification(String qualification) {
    	this.qualifications.add(qualification);
    }
    
    public List<String> getExperiences(){
    	return experiences;
    }
    
    public void addExperience(String experience) {
    	this.experiences.add(experience);
    }
    
    public List<String> getMembership(){
    	return memberships;
    }
    
    public void addMembership(String membership) {
    	this.memberships.add(membership);
    }
    
    public List<String> getPublications(){
    	return publications;
    }
    
    public void addPublication(String publication) {
    	this.publications.add(publication);
    }
    
    public List<String> getAccomplishments(){
    	return accomplishments;
    }
    
    public void addAccomplishment(String accomplishment) {
    	this.accomplishments.add(accomplishment);
    }
    
    public List<Application> getApplications(){
    	return jobApplications;
    }
    
    public void addJobApplication(Application app) {
    	this.jobApplications.add(app);
    }
    
	public void Save() throws Exception {
		Triple triple;
		super.save();
        
		//TODO: Double check foaf ontology for first and last name
		//foaf:name is a junction of name surname and title
		//could add title such as MD PHD and so on
		if(name != null) {
			triple = new Triple(getURI(), "foaf:firstName", name);
			SparqlEndPoint.insertPropertyValue(triple);
		}
		
		if(surname != null) {
			triple = new Triple(getURI(), "foaf:lastName", surname);
			SparqlEndPoint.insertPropertyValue(triple);
		}
		
		if(role != null) {
			triple = new Triple(getURI(), "foaf:title", role);
			SparqlEndPoint.insertPropertyValue(triple);
		}
		
        if(gender != null) {
            triple = new Triple(getURI(), "cv:gender", gender);
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(driversLicense) {
            triple = new Triple(getURI(), "cv:hasDriversLicense", "true");
            SparqlEndPoint.insertPropertyValue(triple);
        }
        else {
            triple = new Triple(getURI(), "cv:hasDriversLicense", "false");
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(cvURI != null) {
        	triple = new Triple(getURI(), "qc:hasResume", cvURI);
            SparqlEndPoint.insertTriple(triple);	
        }
        
        if(competenceArea != null) {
        	triple = new Triple("qc:" + competenceArea, "rdf:type", "qc:CompetenceArea");
    		SparqlEndPoint.insertTriple(triple);
    		
    		triple = new Triple(getURI(), "qc:field", "qc:" + competenceArea);
            SparqlEndPoint.insertTriple(triple);
        }
        
        if(competenceArea != null && competenceAreaDescription != null) {
    		triple = new Triple("qc:" + competenceArea, "rdfs:label", competenceAreaDescription);
    		SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(nationality != null) {
        	triple = new Triple(getURI(), "cv:hasNationality", nationality);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String qualification : qualifications) {
        	triple = new Triple(getURI(), "qc:hasAccomplishment", prefix + qualification);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + qualification, "rdf:type", "qc:Qualification");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String experience : experiences) {
        	triple = new Triple(getURI(), "qc:hasAccomplishment", prefix + experience);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix +experience, "rdf:type", "qc:Experience");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String membership : memberships) {
        	triple = new Triple(getURI(), "qc:hasAccomplishment", prefix + membership);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + membership, "rdf:type", "qc:Membership");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String publication : publications) {
        	triple = new Triple(getURI(), "qc:hasAccomplishment", prefix + publication);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + publication, "rdf:type", "qc:Publication");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(Application app: jobApplications) {
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
                    person.setLabel(object);
                    break;
                    
                case "firstName":
                	person.setName(object);
                	break;

                case "lastName":
                	person.setsurname(object);
                	break;
                	
                case "title":
                	person.setRole(object);
                	break;
                	
                case "gender":
//                	System.out.println("gender: " + gender);
                	person.setGender(object);
                	break;
            
                case "hasDriversLicense": 
//                	System.out.println("hasDriversLicense: " + license);
                	if(object.equals("true"))
                		person.setDriversLicense(true);
                	else if(object.equals("false"))
                		person.setDriversLicense(false);	
                	break;
                	
                case "hasResume":
                	if(object.contains("#"))
                		object = object.substring(object.indexOf("#") + 1);
//                	System.out.println("hasResume: " + resumeURI);
                	person.setCVURI(object);
                	break;
                	
                case "field":
                	if(object.contains("#"))
                		object = object.substring(object.indexOf("#") + 1);
//                	System.out.println("Competence Area: " + competenceArea);
                	person.setCompetenceArea(object);
                	break;
                	

                case "hasAccomplishment":
//                	System.out.println("hasAccomplishment: " + accomplishment);
                	person.addAccomplishment(object);
                	break;

                case "comment":
//                	System.out.println("comment : " + comment);
                	person.setComment(object);
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
