package IP.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class Application extends RDFObject {
	
	private static final String ClassType ="qc:JobApp";  
    private static final String prefix = "qc:";
	private String personURI;
	private String jobURI;
	private String expectedSalary;
	private String salaryCurrency;
	private String availableAt;
	
	public Application() {
		super(ClassType, prefix);
	}
	
	public Application(String URI, String PersonURI, String JobURI, String expectedSalary, String availableAt, String salaryCurrency) {
		super(ClassType, prefix);
		setURI(URI);
		this.personURI = PersonURI;
		this.jobURI = JobURI;
		this.expectedSalary = expectedSalary;
		this.salaryCurrency = salaryCurrency;
		this.availableAt = availableAt;
	}
	
	public String getPersonURI() {
		return this.personURI;
	}
	
	public void setPersonURI(String PersonURI) {
		this.personURI = PersonURI;
	}
	
	public String getJobURI() {
		return jobURI;
	}
	
	public void setJobURI(String JobURI) {
		this.jobURI = JobURI;
	}
	
	public String getExpectedSalary() {
		return expectedSalary;
	}
	
	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
    
    public String getSalaryCurrency() {
    	return salaryCurrency;
    }
    
    public void setSalaryCurrency(String cur) {
    	this.salaryCurrency = cur;
    }
	
	public String getAvailability() {
		return availableAt;
	}
	
	public void setAvailability(String date) {
		availableAt = date;
	}
	
	public static Application getApplication(String URI) {
		String uri = URI;
        if (!uri.startsWith(prefix) && !uri.startsWith("<http")){
        	if(uri.startsWith("http"))
        		uri = "<" + uri + ">";
        	else
        		uri = prefix+URI;
        }
        String properties = SparqlEndPoint.getAllProperties(uri);
        Application app = ParseResponseToApplication(properties);
        app.setURI(uri);
        return app;
	}
	
	private static Application ParseResponseToApplication(String properties) {
		InputStream in = new ByteArrayInputStream(properties.getBytes(StandardCharsets.UTF_8));
        ResultSet results = ResultSetFactory.fromJSON(in);
        
        Application app = new Application();

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
                    app.setLabel(label);
                    break;

                case "comment":
                	String comment = object; 
                	app.setComment(comment);
                	break;

                	
                case "appliedBy":
                	String personURI = object;
                	app.setPersonURI(personURI);
                	break;
                case "appliedFor":
                	String jobURI = object;
                	app.setJobURI(jobURI);
                	break;
                case "hasExpectedSalary":
                	String expSalary = object;
                	app.setExpectedSalary(expSalary);
                	break;
                case "isAvailableAt":
                	String availableAt = object;
                	app.setAvailability(availableAt);
                	break;
                case "expectedSalaryCurrency":
                	String expSalCur = object;
                	app.setSalaryCurrency(expSalCur);
                	break;
                default:
                    break;
            }

        } 
        return app; 
	}

	public void Save() throws Exception {
		Triple triple;
		super.save();
		
		if(personURI != null) {
			triple = new Triple(getURI(), "qc:appliedBy", personURI);
			SparqlEndPoint.insertTriple(triple);
		}
		
		if(jobURI != null) {
			triple = new Triple(getURI(), "qc:appliedFor", jobURI);
			SparqlEndPoint.insertTriple(triple);
		}
		
		if(expectedSalary != null) {
			triple = new Triple(getURI(), "qc:hasExpectedSalary", expectedSalary);
			SparqlEndPoint.insertPropertyValue(triple);
		}
		
		if(availableAt != null) {
			triple = new Triple(getURI(), "qc:isAvailableAt", availableAt);
			SparqlEndPoint.insertPropertyValue(triple);
		}
		
		if(salaryCurrency != null) {
	        triple = new Triple(getURI(), "qc:expectedSalaryCurrency", salaryCurrency);
	        SparqlEndPoint.insertPropertyValue(triple);
        }
	}
}
