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

@RDFNamespaces(
{
    "rdfs = http://www.w3.org/2000/01/rdf-schema",
    "qc = http://www.qualichain.eu/ontology/",
    "cv = http://rdfs.org/resume-rdf/cv.rdfs/" 
})

@RDFBean("qc:Person")
public class Person {
    private static String ClassType ="qc:Person";  
    private static String prefix = "qc:";
    private String URI;
    private String ID;
    private String Label;
    private String Comment;
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
    private List<String> Qualifications;
    private List<String> Experiences;
    private List<String> Memberships;
    private List<String> Publications;
    private List<String> Accomplishments;
    
    public Person() {
    	Qualifications = new ArrayList<>();
    	Experiences = new ArrayList<>();
    	Memberships = new ArrayList<>();
    	Publications = new ArrayList<>();
    	Accomplishments = new ArrayList<>();
    }

    @RDFSubject
    public String getURI()
    {   
        // must return absolute URI
        return URI;
    }
    
    public void setURI(String URI) {
    	if(URI.startsWith(prefix) || URI.startsWith("<"))
    		this.URI = URI;
    	else
    		this.URI = prefix + URI;
    	
    }

    @RDFSubject(prefix="qc:")
    public String getId()
    { 
        // resource URI will be constructed by concatenation of the prefix and returned value
        return ID;
    }
    public void setId(String id) 
    {  
         this.ID = id;
    }

    @RDF("rdfs:label")
    public String getLabel()
    { 
        // this property will be represented as an RDF statement with the foaf:name predicate
        return Label;
    }
    
    public void setLabel(String lable) 
    {   
        this.Label = lable;  
    }
    
    public String getComment() {
    	return Comment;
    }
    
    public void setComment(String comment) {
    	this.Comment = comment;
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
        
        if(Gender != null) {
            triple = new Triple(URI, "cv:gender", Gender);
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(DriversLicense) {
            triple = new Triple(URI, "cv:hasDriversLicense", "true");
            SparqlEndPoint.insertPropertyValue(triple);
        }
        else {
            triple = new Triple(URI, "cv:hasDriversLicense", "false");
            SparqlEndPoint.insertPropertyValue(triple);	
        }
        
        if(CVURI != null) {
        	triple = new Triple(URI, "qc:hasResume", CVURI);
            SparqlEndPoint.insertTriple(triple);	
        }
        
        if(CompetenceArea != null && CompetenceAreaDescription != null) {
        	triple = new Triple("qc:" + CompetenceArea, "rdf:type", "qc:CompetenceArea");
    		SparqlEndPoint.insertTriple(triple);
    		
    		triple = new Triple("qc:" + CompetenceArea, "rdfs:label", CompetenceAreaDescription);
    		SparqlEndPoint.insertPropertyValue(triple);	
    		
            triple = new Triple(URI, "qc:field", "qc:" + CompetenceArea);
            SparqlEndPoint.insertTriple(triple);
        }
        
        if(Nationality != null) {
        	triple = new Triple(URI, "cv:hasNationality", Nationality);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String qualification : Qualifications) {
        	triple = new Triple(URI, "qc:hasAccomplishment", prefix + qualification);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + qualification, "rdf:type", "qc:Qualification");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String experience : Experiences) {
        	triple = new Triple(URI, "qc:hasAccomplishment", prefix + experience);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix +experience, "rdf:type", "qc:Experience");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String membership : Memberships) {
        	triple = new Triple(URI, "qc:hasAccomplishment", prefix + membership);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + membership, "rdf:type", "qc:Membership");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        for(String publication : Publications) {
        	triple = new Triple(URI, "qc:hasAccomplishment", prefix + publication);
        	SparqlEndPoint.insertTriple(triple);
        	
        	triple = new Triple(prefix + publication, "rdf:type", "qc:Publication");
        	SparqlEndPoint.insertPropertyValue(triple);
        }
}
	
    public static List<Person> getPersons(){
        String SparqlJsonResults = SparqlEndPoint.getInstances(Person.ClassType);
        return ParseResponse(SparqlJsonResults);
    }

	public static Person getPerson(String URI){
        
        String properties = SparqlEndPoint.getAllProperties(URI);
        //System.out.println(properties);
        Person person = ParseResponseToPerson(properties);
        person.setURI(URI);
        return person;
    }
	
	//TODO:getPersonsByField should get the persons with the field through the fields label instead or partial match
	public static List<Person> getPersonsByField(String field){
		String SparqlJsonFieldResults = SparqlEndPoint.getInstancesByPartialLabel("qc:CompetenceArea", field);
		String property = ParseResponseToProperty(SparqlJsonFieldResults);
		String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(ClassType, "qc:field","<" + property + ">" );
		return ParseResponse(SparqlJsonResults);
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
	
	private static String ParseResponseToProperty(String SparqlJsonResults) {
        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);
        
        if(results.hasNext()) {
        	
        	QuerySolution soln = results.nextSolution();
        	return String.valueOf(soln.getResource("subject"));
        }
        
		return null;
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
            person.setId( ID);   
            //cv.setURI(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

            Persons.add(person); 
        } 
        return Persons; 
	}
	
	//Maybe add a String defining the type of accomplishment and use it to further define the accomplishment and its details
	private void saveAccomplishment(String accomplishment) {
    	Triple triple = new Triple(URI,"qc:hasAccomplishment", accomplishment);
    	SparqlEndPoint.insertPropertyValue(triple);
		
	}

}
