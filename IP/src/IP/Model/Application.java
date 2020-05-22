package IP.Model;

public class Application extends RDFObject {
	
	private static final String ClassType ="qc:JobApp";  
    private static final String prefix = "qc:";
	private String PersonURI;
	private String JobURI;
	private String expectedSalary;
	private String availableAt;
	
	public Application() {
		super(ClassType, prefix);
	}
	
	public Application(String URI, String PersonURI, String JobURI, String expectedSalary, String availableAt) {
		super(ClassType, prefix);
		setURI(URI);
		this.PersonURI = PersonURI;
		this.JobURI = JobURI;
		this.expectedSalary = expectedSalary;
		this.availableAt = availableAt;
	}
	
	public String getPersonURI() {
		return this.PersonURI;
	}
	
	public void setPersonURI(String PersonURI) {
		this.PersonURI = PersonURI;
	}
	
	public String getJobURI() {
		return JobURI;
	}
	
	public void setJobURI(String JobURI) {
		this.JobURI = JobURI;
	}
	
	public String getExpectedSalary() {
		return expectedSalary;
	}
	
	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	
	public String getAvailability() {
		return availableAt;
	}
	
	public void setAvailability(String date) {
		availableAt = date;
	}
	
	public void Save() throws Exception {
		Triple triple;
		super.save();
		
		if(PersonURI != null) {
			triple = new Triple(getURI(), "qc:appliedBy", PersonURI);
			SparqlEndPoint.insertTriple(triple);
		}
		
		if(JobURI != null) {
			triple = new Triple(getURI(), "qc:appliedFor", JobURI);
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
	}
}
