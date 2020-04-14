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
public class Skill //implements Serializable 
{ 	
    private static String ClassType ="cv:Skill";
    private static String prefix ="cv:";  
    private String SkillURI;
    private String SkillID;
    private String SkillLabel;
    private String proficiencyLevel;
    private String priorityLevel;
    //priority Level in the comment section of the skill
    private String SkillComment;
    
    /**
     * Makes a Skill object with no set variables
     */
    public Skill(){

    }
    
    /**
     * Makes a Skill object with a defined identifier URI
     * @param URI Identifier URI in String format, should follow the convention for RDF URIs of "prefix:ID"
     */
    public Skill(String URI){
        this.SkillURI = URI;
    }

    /**
     * Returns the Skill URI
     * @return Skill URI in String form
     */
    @RDFSubject
    public String getUri()
    {   
        // must return absolute URI
        return SkillURI;
    }
    
    /**
     * Sets the Skill SkillURI variable with the parameter URI
     * @param URI Identifier URI in String format, should follow the convention for RDF URIs of "prefix:ID"
     */
    public void setURI(String URI) {
    	if(URI.startsWith(prefix) || URI.startsWith("<"))
    		this.SkillURI = URI;
    	else
    		this.SkillURI = prefix + URI;
    }

    /**
     * Returns a unique identifier for the Skill, should be the part after the ":" of the URI
     * @return Unique Skill ID without a prefix
     */
    @RDFSubject(prefix="cv:")
    public String getId()
    { 
        // resource URI will be constructed by concatenation of the prefix and returned value
        return SkillID;
    }

    /**
     * Sets the SkillID variable to the value of parameter id
     * @param id String value to set the SkillID variable in the Skill object
     */
    public void setId(String id) 
    {  
         this.SkillID = id;
    }

    /**
     * Returns the Skill Label that represents the Skill name
     * @return SkillLabel variable, represents the Skill name
     */
    @RDF("rdfs:label")
    public String getLabel()
    { 
        return SkillLabel;
    }
    
    /**
     * Sets the name of the Skill as the variable SkillLabel
     * @param label String value to set the SkillLabel with, represents the name of the Skill
     */
    public void setLabel(String label) 
    {   
        this.SkillLabel = label;  
    }
    
    /**
     * Returns the SkillComment variable, which represents a description of the represented Skill
     * @return SkillComment variable, represents a description of the represented Skill
     */
    public String getComment() {
    	return this.SkillComment;
    }
    
    /**
     * Sets the SkillComment variable in the Skill object
     * @param comment String representation of the description of a Skill
     */
    public void setComment(String comment) {
    	this.SkillComment = comment;
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
     */
	public void Save() {

            Triple triple = new Triple(SkillURI, "rdf:type", ClassType);
            SparqlEndPoint.insertTriple(triple.toString());

			triple = new Triple(SkillURI, "rdfs:label", SkillLabel);
            SparqlEndPoint.insertPropertyValue(triple);

			triple = new Triple(SkillURI, "rdfs:comment", SkillComment);
            SparqlEndPoint.insertTriple(triple.toPropertyValueString());   
            
            if(proficiencyLevel != null) {
            	triple = new Triple(SkillURI, "saro:atProficiencyLevel", proficiencyLevel);
            	SparqlEndPoint.insertPropertyValue(triple);
            }
            
            if(priorityLevel != null) {
            	triple = new Triple(SkillURI, "qc:hasPriority", priorityLevel);
                SparqlEndPoint.insertPropertyValue(triple);
            }
            
	}
	
	/**
	 * Returns the Skill object representation of the Skill with the URI from the parameter uri by performing a GET request to the Database
	 * @param uri The String representation of the URI of the desired Skill
	 * @return Skill object representation of a Skill in the Database with the same URI as the parameter uri
	 */
	public static Skill getSkill(String uri) {
		String properties = SparqlEndPoint.getAllProperties(uri);
	    //System.out.println(properties);
        Skill skill = ParseResponseToSkill(properties);
        String id = uri.split("#")[0].replace(">", "");
	    skill.setId(id);
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
        Skill skill = Skill.getSkill("<"+uri+">");
        
        return skill;
	}

    /**
     * Returns a list of all the Skills stored in the Database as Skill objects
     * @return List of all Skills  stored in the Database accessible by the SparqlEndPoint class as Skill objects
     */
    public static List<Skill> getSkills(){
        String SparqlJsonResults = SparqlEndPoint.getInstances(ClassType);
        return ParseResponse(SparqlJsonResults);
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
            skill.setId( ID);   
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