package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class WorkHistory extends RDFObject {

	private static final String ClassType ="cv:WorkHistory";
    private static final String prefix ="cv:"; 
    private String position;
    private String from;
    private String to;
    private String employer;
	
	public WorkHistory() {
		super(ClassType, prefix);
	}
	
	public WorkHistory(String URI) {
		super(ClassType, prefix);
		setURI(URI);
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPosition() {
		return position;
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
	
	public void setEmployer(String emp) {
		this.employer = emp;
	}
	
	public String getEmployer() {
		return employer;
	}
	
	public static WorkHistory getWorkHistory(String URI) {
		String uri = URI;
    	if(!uri.startsWith(prefix) && !uri.startsWith("<http")) {
        	if(uri.startsWith("http"))
        		uri ="<"+ uri + ">";
        	else
        		uri = prefix+uri;
		}
		String properties = SparqlEndPoint.getAllProperties(uri);
	    //System.out.println(properties);
        WorkHistory wh = ParseResponseToWorkHistory(properties);
	    wh.setURI(uri);
	    
	    return wh;
	}

	private static WorkHistory ParseResponseToWorkHistory(String SparqlJsonResults) {
			InputStream in = new ByteArrayInputStream(SparqlJsonResults.getBytes(StandardCharsets.UTF_8));
	        ResultSet results = ResultSetFactory.fromJSON(in);

	        
	        WorkHistory wh = new WorkHistory();

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
//	                    System.out.println("type: "+type);
	                    break;

	                case "label":
	                    String label = object;   
//	                    System.out.println("label: " + label);
	                    wh.setLabel(label);
	                    break;
	                    
	                case "comment":
	                	String comment = object; 
	                	wh.setComment(comment);
	                	break;
	                	
	                case "from":
	                	String from = object;  
	                	wh.setFrom(from);
	                	break;
	                	
	                case "to":
	                	String to = object;  
	                	wh.setTo(to);
	                	break;
	                	
	                case "employer":
	                	String employer = object;
	                	wh.setEmployer(employer);
	                	break;
	                case "position":
	                	String position = object;
	                	wh.setPosition(position);
	                	break;
	                	
	                default:
	                    break;
	            }
	        }
	        return wh;
	}

	public void Save() {
		super.rootRDFSave();
        
        Triple triple;
        
        if(position != null) {
        	triple = new Triple(getURI(), "qc:position", position);
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
        
        if(employer != null) {
        	triple = new Triple(getURI(), "qc:employer", employer);
        	SparqlEndPoint.insertPropertyValue(triple);
        }
	}
}
