package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class RDFObject {

	private String CLASSTYPE = null;
	private String PREFIX = null;
	private String URI = null;
	private String ID = null;
	private String label = null;
	private String comment = null;
	
	/**
	 * RDFObject simple constructor with rdf type and prefix standard
	 * @param classType String representing the type of the RDFObject, format is "prefix:name" for example "cv:CV", "qc:Person"
	 * @param prefix String representing the prefix used for saving triples related to the RDFObject
	 */
	public RDFObject(String classType, String prefix) {
		CLASSTYPE = classType;
		PREFIX = prefix;
//		autoGenerateIDURI();
	}
	
	/**
	 * RDFObject constructor that creates an object with classType, prefix, id, label and comment to define a simple RDFObject
	 * @param classType String representing the type of the RDFObject, format is "prefix:name" for example "cv:CV", "qc:Person"
	 * @param prefix String representing the prefix used for saving triples related to the RDFObject
	 * @param id Unique id to identify different RDFObjects
	 * @param label Name of the RDFObject
	 * @param comment Description of the RDFObject
	 */
	public RDFObject(String classType, String prefix, String id, String label, String comment) {
		CLASSTYPE = classType;
		PREFIX = prefix;
		URI = PREFIX + id;
		this.ID = id;
		this.label = label;
		this.comment = comment;
	}
	
	/**
	 * Gets RDFObject URI
	 * @return RDFObject URI
	 */
	public String getURI() {
		return URI;
	}
	
	/**
	 * Gets RDFObject ID
	 * @return RDFObject ID
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Gets RDFObject label
	 * @return RDFObject label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gets RDFObject comment
	 * @return RDFObject comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Sets the URI for the RDFObject
	 * @param URI String format of the URI to be set as the RDFObject URI
	 */
	public void setURI(String URI) {
		this.URI = URI;
		if(ID == null) {
			if(URI.contains(":"))
				ID = URI.substring(URI.indexOf(":"));
			else if(URI.contains("#"))
				ID = URI.substring(URI.indexOf("#"), URI.lastIndexOf(">"));
			else if(URI.contains("http"))
				ID = URI.substring(URI.indexOf("http")); //TODO: temp, figure out a way to construct the ID out of this URI format
		}
			
	}
	
	/**
	 * Sets the ID for the RDFObject
	 * @param ID String format of the ID to be set as the RDFObject ID
	 */
	public void setID(String ID) {
		this.ID = ID;
	}
	
	/**
	 * Sets the label for the RDFObject
	 * @param label String format of the label to be set as the RDFObject label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * 
	 * @param Description String format of the comment to be set as the RDFObject comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	/**
	 * The method saves the data stored in the RDFObject to the server being accessed by the SparqlEndPoint class as RDF triples
	 * @throws Exception 
	 */
	public void save() {
		
		if(ID == null || URI == null || (!URI.startsWith(PREFIX) && !URI.startsWith("<http"))) {
			autoGenerateIDURI();
		}
		
		Triple triple = new Triple(URI, "rdf:type", CLASSTYPE);
        SparqlEndPoint.insertTriple(triple);
        
        if(label != null) {
        	triple = new Triple(URI, "rdfs:label", label);
            SparqlEndPoint.insertPropertyValue(triple);
        }
		

        if(comment != null) {
        	triple = new Triple(URI, "rdfs:comment", comment);
            SparqlEndPoint.insertTriple(triple.toPropertyValueString());   
		}
		
		
	}
	
	/**
	 * Deletes every propriety related to the URI, no return for confirmation of what was deleted
	 * Requires the URI to have the correct format, not guaranteed to work otherwise
	 * @param URI URI of the object meant to be deleted
	 */
	public static void quickDeleteByURI(String URI) {
		SparqlEndPoint.deleteObjectByUri(URI);
	}
	
	private void autoGenerateIDURI() {

		ID ="id" + UUID.randomUUID().toString();
		URI = PREFIX+ID;
	}


	public static boolean exists(String label){
		String properties = SparqlEndPoint.getInstancesByLabel(label);
        String uri = SparqlEndPoint.ParseResponseToURI(properties);
        if (uri !=null)
            return true;
        else
            return false;
	}

}
