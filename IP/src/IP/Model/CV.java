package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
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
public class CV extends RDFObject {
	
    private static final String ClassType ="cv:CV";     
    private static final String prefix ="cv:";     
    private String title = null;
    private String personURI = null;
    private String description = null;
    private String targetSector = null;
    private String otherInfo = null;
    private List<WorkHistory> workHistory;
    private List<Education> education;
    private List<Course> courses;
    private List<Skill> skills;
    private List<String> skillURIs;
    private Application jobApplication = null;

    //TODO: Add remove methods for Lists
    
    //TODO: Add constructors for different cases if found necessary
    /**
     * Constructor with empty WorkHistory, Education, Courses, and Skills lists and no values attributed 
     */
    public CV() {
    	super(ClassType, prefix);
    	workHistory = new ArrayList<>();
    	education = new ArrayList<>();
    	courses = new ArrayList<>();
    	skills = new ArrayList<>();
    	skillURIs = new ArrayList<>();
    }
    
    /**
     * Constructor with input for every variable and list for the CV object
     * @param id CV id, used to derive the URI for now, should be changed to a random unique URI generator
     * @param label CV "name", another identifier that also describes the CV loosely
     * @param comment CV comment, any short comment on the CV
     * @param title CV title, can be a short descriptor of the person who's CV this is, or a description of the goal of the CV
     * @param personUri URI of the person to whom this CV belongs to
     * @param desc CV description, another way of describing the person's intention with the CV 
     * @param targetSect Description of the target job the CV is aimed at
     * @param info information about the person that could be useful for the job position the CV is applying for
     * @param expSalary Expected salary amount the Person wants to receive for the Job
     * @param salaryCurrency The currency the Person wants to/expects to be payed in
     * @param workHist History of past job experiences the Person has had
     * @param educ Educational history the Person has
     * @param courses Courses the Person has attended
     * @param skills Skills the Person holds/has certification in
     */
    public CV(String id, String label, String comment, String title, String personUri,
    		String desc, String targetSect, String info, String expSalary, String salaryCurrency,
    		List<WorkHistory> workHist, List<Education> educ, List<Course> courses, List<Skill> skills, Application jobApp) {
    	super(ClassType, prefix, id, label, comment);
    	this.title = title;
    	this.personURI = personUri;
    	this.description = desc;
    	this.targetSector = targetSect;
    	this.otherInfo = info;
    	if(workHist == null) {
    		this.workHistory = new ArrayList<WorkHistory>();
    	}
    	else
    		this.workHistory = workHist;
    	if(educ == null) {
    		this.education = new ArrayList<Education>();
    	}
    	else
    		this.education = educ;
    	if(courses == null) {
    		this.courses = new ArrayList<Course>();
    	}
    	else
    		this.courses = courses;
    	if(skills == null) {
    		this.skills = new ArrayList<Skill>();
    		this.skillURIs = new ArrayList<String>();
    	}
    	else {
    		this.skills = skills;
        	this.skillURIs = new ArrayList<>(skills.size());
        	for(Skill skill : skills) {
        		this.skillURIs.add(skill.getURI());
        	}
    	}

    	jobApplication = jobApp;
    }

    
    public String getTitle() {
    	return title;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getPersonURI() {
    	return personURI;
    }
    
    public void setPersonURI(String personURI) {
    	this.personURI = personURI;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public String getTargetSector() {
    	return targetSector;
    }
    
    public void setTargetSector(String sector) {
    	this.targetSector = sector;
    }
    
    public String getOtherInfo() {
    	return otherInfo;
    }
    
    public void setOtherInfo(String info) {
    	this.otherInfo = info;
    }
    
    public void addOtherInfo(String info) {
    	this.otherInfo = this.otherInfo + "\n" + info + "\n";
    }
    
    public List<WorkHistory> getWorkHistory()	{
    	return this.workHistory;
    }
    
    public void addWorkHistory(WorkHistory workHistory) {
    	this.workHistory.add(workHistory);
    }
    
    public List<Education> getEducation()	{
    	return this.education;
    }
    
    public void addEducation(Education education) {
    	this.education.add(education);
    }
    
    public List<Course> getCourses()	{
    	return this.courses;
    }
    
    public void addCourse(Course course) {
    	this.courses.add(course);
    }
    
    public List<Skill> getSkills()	{
    	return skills;
    }
    
    public List<String> getSkillURIs(){
    	return skillURIs;
    }
    
    //add Skill by uri or ID
    public void addSkill(String skill) {
    	if(!skill.startsWith(prefix)) {
    		this.skills.add(Skill.getSkill(prefix + skill));
    		this.skillURIs.add(prefix + skill);
    	}
    		
    	else {
    		this.skills.add(Skill.getSkill(skill));
    		this.skillURIs.add(skill);
    	}
    } 
    
    //add Skill by object
    public void addSkill(Skill skill) {
    	this.skills.add(skill);
    	this.skillURIs.add(skill.getURI());
    }
    
    //ease of testing method, could be used for practical uses too in future
    public void addSkills(List<Skill> skills) {
    	this.skills.addAll(skills);
    }
    
    
    public boolean hasSkill(String skillLabel){
    	for(Skill curSkill : skills) {
    		String label = curSkill.getLabel();
    		
    		if(label.equalsIgnoreCase(skillLabel)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //TODO: Generalize all other competences to skills and then specify what each skill is?
    public boolean hasWorkExperience ( WorkHistory workExperience) {
    	for(WorkHistory job : workHistory) {
    		//not sure I should keep the IgnoreCase since it's a URI 
    		if(job.getURI().equals(workExperience.getURI())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean hasEducation ( Education educ) {
    	for(Education certificate : education) {
    		//not sure I should keep the IgnoreCase since it's a URI 
    		if(certificate.getURI().equalsIgnoreCase(educ.getURI())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean hasCourse ( Course course) {
    	for(Course certificate : courses) {
    		//not sure I should keep the IgnoreCase since it's a URI 
    		if(certificate.getURI().equalsIgnoreCase(course.getURI())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public Application getApplication(){
    	return jobApplication;
    }
    
    public void setJobApplication(Application app) {
    	jobApplication = app;
    }
    
    
	public void Save() throws Exception {

		//Insert CV
		Triple triple;
		super.save();
		
        if(title != null) {
	        //Insert CV title
	        triple = new Triple(getURI(), "cv:cvTitle", title);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(personURI != null) {
	        //Insert Person CV association
	        triple = new Triple(getURI(), "cv:aboutPerson", personURI);
	        SparqlEndPoint.insertTriple(triple);
        }
        
        if(description != null) {
	        //Insert CV description
	        triple = new Triple(getURI(), "cv:hasDescription", description);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(targetSector != null) {
	        triple = new Triple(getURI(), "cv:targetJobDescription", targetSector);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
             
        if(otherInfo != null) {
	        triple = new Triple(getURI() , "cv:hasOtherInfo", otherInfo);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
        
        //Insert CV work history list
        for(WorkHistory workHistory : workHistory) {
        	workHistory.Save();
        	triple = new Triple(getURI(), "cv:hasWorkHistory", workHistory.getURI());
        	SparqlEndPoint.insertTriple(triple);
        }
        
        //Insert CV education list
        for(Education education : education) {
        	education.Save();
        	triple = new Triple(getURI(), "cv:hasEducation", education.getURI());
        	SparqlEndPoint.insertTriple(triple);
        	
        }
        
        //Insert CV courses
        for(Course course : courses) {
        	course.Save();
        	triple = new Triple(getURI(), "cv:hasCourse", course.getURI());
        	SparqlEndPoint.insertTriple(triple);
        }
        
        //Insert CV skills
        for(Skill skill : skills) {
        	skill.Save();
        	triple = new Triple(getURI(), "cv:hasSkill", skill.getURI());
        	SparqlEndPoint.insertTriple(triple);
        }
        
        if(jobApplication != null) {
        	jobApplication.Save();
        	triple = new Triple(getURI(), "qc:hasAppliedTo", jobApplication.getURI());
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
        
        String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(CV.ClassType, "cv:hasSkill", skill.getURI());
        return ParseResponse(SparqlJsonResults);
    }

    public static CV getCV(String URI){
    	String uri = URI;
        if (!uri.startsWith(CV.prefix) && !uri.startsWith("<http")){
        	if(uri.startsWith("http"))
        		uri = "<" + uri + ">";
        	else
        		uri = CV.prefix+URI;
        }
        String properties = SparqlEndPoint.getAllProperties(uri);
        CV cv = ParseResponseToCV(properties);
        cv.setURI(uri);
        return cv;
    }
    
    public static CV getCVbyPerson(String name) {

    	Person person = Person.getPersonByName(name);
    	String SparqlJsonResults = SparqlEndPoint.getInstancesByProperty(CV.ClassType, "cv:aboutPerson", person.getURI() );
    	return ParseResponse(SparqlJsonResults).get(0);
    }
    
    public static CV deleteObject(String URI) {
    	CV cv = CV.getCV(URI);
    	SparqlEndPoint.deleteObjectByUri(cv.getURI());
    	return cv;
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
            cv.setID( ID);   
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

            RDFNode Onode = soln.get("object");
            String object="";
            if (Onode.isResource()) {
                object = String.valueOf(soln.getResource("object"));
            }
            else{
                object = String.valueOf(soln.getLiteral("object"));   
            }

            System.out.println("field:"+res.getLocalName()+". value:"+object);

            /* to repalce the switch
            try {
                Field field = CV.class.getField(res.getLocalName());
                field.set(cv, object); 
                System.out.println(res.getLocalName()+":"+field.get(cv).toString());

            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            */
            
            switch (res.getLocalName()) {
                case "type":
                    String type = object;   
//                    System.out.println("type: "+type);
                    break;

                case "label":
                    String label = object;   
//                    System.out.println("label: " + label);
                    cv.setLabel(label);
                    break;
                    
                case "cvTitle":
                	String title = object;  
//                	System.out.println("cvTitle: " + title);
                	cv.setTitle(title);
                	break;
            
                case "aboutPerson":
                	String personURI = object;  
                	if(personURI.contains("#"))
                		personURI = personURI.substring(personURI.indexOf("#") + 1);
//                	System.out.println("aboutPerson: " + personURI);
                	cv.setPersonURI("qc:" + personURI);
                	break;
                	
                case "hasSkill":
                	String skillURI = object;  
                	if(skillURI.contains("#"))
                		skillURI = skillURI.substring(skillURI.indexOf("#") + 1);
//                	System.out.println("hasSkill: " + skillURI);
                	cv.addSkill(skillURI);
                	break;
                	
                case "hasWorkHistory":
                	String workHistory = object;  
//                	System.out.println("Work History: " + workHistory);
                	cv.addWorkHistory(WorkHistory.getWorkHistory(workHistory));
                	break;
                	

                case "hasDescription":
                	String description = object;  
//                	System.out.println("hasDescription: " + description);
                	cv.setDescription(description);
                	break;

                case "comment":
                	String comment = object;  
//                	System.out.println("comment : " + comment);
                	cv.setComment(comment);
                	break;
                	

                case "hasEducation":
                	String education = object;  
//                	System.out.println("hasEducation: " + education);
                	cv.addEducation(Education.getEducation(education));
                	break;
                	

                case "hasCourse":
                	String course = object;  
//                	System.out.println("hasCourse: " + course);
                	cv.addCourse(Course.getCourse(course));
                	break;
                	
                case "hasAppliedTo":
                	String applicationURI = object;
                	cv.setJobApplication(Application.getApplication(applicationURI));
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
    
    public String getInfo() {
    	return "URI: " + getURI() + "\n" +
    			"Label: " + getLabel() + "\n" + 
    			"Person: " + personURI;
    }

}
