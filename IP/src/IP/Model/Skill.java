package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
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

/*
//for <classpathentry kind="lib" path="lib/RDFBeans-2.1/RDFBeans-2.1.jar"/>
import com.viceversatech.rdfbeans.annotations.RDF;
import com.viceversatech.rdfbeans.annotations.RDFBean;
import com.viceversatech.rdfbeans.annotations.RDFNamespaces;
import com.viceversatech.rdfbeans.annotations.RDFSubject;
*/

@RDFNamespaces(
{      
    "rdfs = http://www.w3.org/2000/01/rdf-schema",
    "qc = http://www.qualichain.eu/ontology/",
    "cv = http://rdfs.org/resume-rdf/cv.rdfs/" 
})

@RDFBean("cv:Skill")
public class Skill extends RDFObject//implements Serializable 
{ 	
    private static final String ClassType ="cv:Skill";
    private static final String prefix ="cv:";  
    private String proficiencyLevel;
    private String priorityLevel;
    
    
    //TODO: Add constructors for different cases if found necessary
    
    /**
     * Makes a Skill object with no set variables
     */
    public Skill() {
    	super(ClassType, prefix);
    }
    
    public Skill(String id, String label, String profLevel, String priority, String comment) {
    	super(ClassType, prefix, id, label, comment);
    	this.proficiencyLevel = profLevel;
    	this.priorityLevel = priority;
//    	if(profLevel == null)
//    		this.proficiencyLevel = "";
//    	else
//    		this.proficiencyLevel = profLevel;
//    	
//    	if(priority == null)
//    		this.priorityLevel = "";
//    	else
//    		this.priorityLevel = priority;
    }
    

    /**
     * Returns the proficiency level at which this Skill is known
     * @return proficiencyLevel variable, a String that represents the level of proficiency at which this Skill is known
     */
    public String getProficiencyLevel() {
    	return this.proficiencyLevel;
    }
    
    /**
     * Sets the proficiency level at which this Skill is known
     * @param profLevel String representation of the level of proficiency at which this Skill is known, that is used
     * to set the proficiencyLevel variable of the Skill
     */
    public void setProficiencyLevel(String profLevel) {
    	this.proficiencyLevel = profLevel;
    }
    
    /**
     * Return the priorityLevel variable of the Skill, which is a String representation of how important this Skill is for a JobPosting
     * @return priorityLevel variable, which represents how important this Skill is for a JobPosting
     */
    public String getPriorityLevel() {
    	return this.priorityLevel;
    }
    
    /**
     * Sets the priority level of the Skill for a JobPosting
     * @param level String representation of the priority level that will be set to the priorityLevel variable of the SKill
     */
    public void setPriorityLevel(String level) {
    	priorityLevel = level;
    }
    
    /**
     * Transforms all the variables of the Skill into Triple class objects for insertion in the Database handled by the SparqlEndPoint class
     * @throws Exception 
     */
	public void Save() throws Exception {
            
            super.save();
            
            Triple triple;
            
            if(proficiencyLevel != null) {
            	triple = new Triple(getURI(), "saro:atProficiencyLevel", proficiencyLevel);
            	SparqlEndPoint.insertPropertyValue(triple);
            }
            
            if(priorityLevel != null) {
            	triple = new Triple(getURI(), "qc:hasPriority", priorityLevel);
                SparqlEndPoint.insertPropertyValue(triple);
            }
            
	}
	
	/**
	 * Returns the Skill object representation of the Skill with the URI from the parameter uri by performing a GET request to the Database
	 * @param uri The String representation of the URI of the desired Skill
	 * @return Skill object representation of a Skill in the Database with the same URI as the parameter uri
	 */
	public static Skill getSkill(String URI) {
        String uri = URI;
    	if(!uri.startsWith(prefix) && !uri.startsWith("<http")) {
        	if(uri.startsWith("http"))
        		uri ="<"+ uri + ">";
        	else
        		uri = prefix+uri;
        }
		String properties = SparqlEndPoint.getAllProperties(uri);
	    //System.out.println(properties);
        Skill skill = ParseResponseToSkill(properties);
	    skill.setURI(uri);
	    
	    return skill;
	}

	/**
	 * Returns a Skill object that has the same Label as the parameter label
	 * @param label String representation of the name of the Skill
	 * @return Skill object stored in the Database with the same Label as the parameter label
	 */
    public static Skill getSkillByLabel(String label) {
		String properties = SparqlEndPoint.getInstancesByLabel(ClassType,label);
        String uri = SparqlEndPoint.ParseResponseToURI(properties);
        if (uri !=null){
            Skill skill = Skill.getSkill(uri);
            return skill;
        }
        else
            return null;
	}

    /**
     * Returns a list of all the Skills stored in the Database as Skill objects
     * @return List of all Skills  stored in the Database accessible by the SparqlEndPoint class as Skill objects
     */
    public static List<Skill> getSkills(){
        String SparqlJsonResults = SparqlEndPoint.getInstances(ClassType);
        return ParseResponse(SparqlJsonResults);
    }
    
    public static Skill deleteObject(String URI) {
    	Skill skill = Skill.getSkill(URI);
    	SparqlEndPoint.deleteObjectByUri(skill.getURI());
    	return skill;
    }
    
    /**
     * Parses a Json Response from the Database into Skill objects and creates a List of all the Skills gathered from the Response
     * @param SparqlJsonResults Database query response in Json format with RDF data structure 
     * @return A list of Skill objects derived from the Json SparqlJsonResults
     */
    private static List<Skill> ParseResponse(String SparqlJsonResults){
    	
        List<Skill> Skills = new ArrayList<>();  
        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);
        
        while (results.hasNext()) {

            QuerySolution soln = results.nextSolution();
            Resource res= soln.getResource("subject");

            //String ID = String.valueOf(res);            
            String ID =  res.getLocalName();            
            Skill skill = getSkill(prefix + ID);
            skill.setID( ID);   
            //cv.setURI(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

            Skills.add(skill);  
        } 
        return Skills; 
    }

    /**
     * Returns a single Skill object from a String in Json format
     * @param SparqlJsonResults Database query response in Json format of a single Skill with RDF data structure 
     * @return Skill object derived from parsed Json SparqlJsonResults
     */
    private static Skill ParseResponseToSkill(String SparqlJsonResults) {

        InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);

//        System.out.println("============================================");
        
        Skill skill = new Skill();

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
                    skill.setLabel(label);
                    break;
                    
                case "comment":
                	String comment = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("comment : " + comment);
                	skill.setComment(comment);
                	break;
                case "atProficiencyLevel":
                	String proficiency = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("comment : " + comment);
                	skill.setProficiencyLevel(proficiency);
                	break;
                case "hasPriority":
                	String priority = String.valueOf(soln.getLiteral("object"));  
//                	System.out.println("comment : " + comment);
                	skill.setPriorityLevel(priority);
                	break;
                	
                default:
                    break;
            }
        }
        return skill;
    }
        
}