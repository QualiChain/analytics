package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class Education extends RDFObject{

	private static final String ClassType ="cv:Education";
    private static final String prefix ="cv:"; 
    private String title;
    private String from;
    private String to;
    private String org;
    private String description;
	
	public Education() {
		super(ClassType, prefix);
	}
	
	public Education(String URI) {
		super(ClassType, prefix);
		setURI(URI);
	}
	
	public void setTitle(String Title) {
		this.title = Title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setFrom(String from) {
		this.from = from;
		
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setTo(String to) {
		this.to = to;
		
	}
	
	public String getTo() {
		return to;
	}
	
	public void setOrganization(String org) {
		this.org = org;
	}
	
	public String getOrganization() {
		return org;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static Education getEducation(String URI) {
		String uri = URI;
    	if(!uri.startsWith(prefix) && !uri.startsWith("<http")) {
        	if(uri.startsWith("http"))
        		uri ="<"+ uri + ">";
        	else
        		uri = prefix+uri;
		}
		String properties = SparqlEndPoint.getAllProperties(uri);
	    //System.out.println(properties);
		Education ed = ParseResponseToEducation(properties);
	    ed.setURI(uri);
	    
	    return ed;
	}
	
	private static Education ParseResponseToEducation(String SparqlJsonResults) {
		InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);

        
        Education ed = new Education();

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
                    ed.setLabel(label);
                    break;
                    
                case "title":
                	String title = object;  
                	ed.setTitle(title);
                	break;
            
                case "from":
                	String startDate = object;  
                	ed.setFrom(startDate);
                	break;
                	
                case "to":
                	String endDate = object;  
                	ed.setTo(endDate);
                	break;
                	
                case "organization":
                	String org = object;  
                	ed.setOrganization(org);
                	break;
                	
                case "description":
                	String desc = object;
                	ed.setDescription(desc);
                	
                default:
                    break;
            }
            
            //String ID = String.valueOf(soln.getResource("predicate"));
            //System.out.println(ID);   
            //cv.setId(ID);   
            //cv.setId(StringUtils.substringAfter(ID,"http://rdfs.org/resume-rdf/cv.rdfs#"));   

        } 
        return ed; 
	}

	public void Save() {
		super.save();
        
        Triple triple;
        
        if(title != null) {
        	triple = new Triple(getURI(), "qc:title", title);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(from != null) {
        	triple = new Triple(getURI(), "qc:from", from);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(to != null) {
        	triple = new Triple(getURI(), "qc:to", to);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(org != null) {
        	triple = new Triple(getURI(), "qc:organization", org);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
        
        if(description != null) {
        	triple = new Triple(getURI(), "qc:description", description);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
	}

}
