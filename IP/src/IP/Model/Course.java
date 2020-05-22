package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class Course extends RDFObject {
	
	private static final String ClassType ="cv:Course";
    private static final String prefix ="cv:"; 
    private String qualification;
    private String developedBy;
    private ArrayList<String> relatedTo;

	public Course() {
		super(ClassType, prefix);
	}
	
	public Course(String URI) {
		super(ClassType, prefix);
		setURI(URI);
	}
	
	public void setQualification(String qual) {
		this.qualification = qual;
	}
	
	public String getQualification() {
		return qualification;
	}
	
	public static Course getCourse(String URI) {
		String uri = URI;
    	if(!uri.startsWith(prefix) && !uri.startsWith("<http")) {
        	if(uri.startsWith("http"))
        		uri ="<"+ uri + ">";
        	else
        		uri = prefix+uri;
		}
		String properties = SparqlEndPoint.getAllProperties(uri);
	    //System.out.println(properties);
		Course crs = ParseResponseToCourse(properties);
        crs.setURI(uri);
	    
	    return crs;
	}
	
	
	
	private static Course ParseResponseToCourse(String SparqlJsonResults) {
		InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);

        
        Course crs = new Course();

        while (results.hasNext()) {

            QuerySolution soln = results.nextSolution();

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
                    break;

                case "label":
                    String label = object;   
                    crs.setLabel(label);
                    break;
                    
                case "certificate":
                	String certificate = object;  
                	crs.setQualification(certificate);
                	break;
                	
                default:
                    break;
            }
            
            //String ID = String.valueOf(soln.getResource("predicate"));
            //System.out.println(ID);   
            //cv.setId(ID);   
            //cv.setId(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

        } 
        return crs; 
	}

	public void Save() {
		super.save();
        
        Triple triple;
        
        if(qualification != null) {
        	triple = new Triple(getURI(), "saro:leadsToQualification", qualification);
        	SparqlEndPoint.insertTriple(triple);
        }
        
	}

}
