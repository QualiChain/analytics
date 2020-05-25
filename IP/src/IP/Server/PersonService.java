package IP.Server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import IP.GsonParser;
import IP.Model.JobPosting;
import IP.Model.Person;
import IP.Model.SparqlEndPoint;

@Path("/")
public class PersonService {

	
	@GET
	@Path("/profiles")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> GetProfiles() {
		return Person.getPersons();	
	}
	
	@POST
	@Path("/profiles")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response CreatePerson(String data) {
		GsonParser parser;
		try {
			parser = new GsonParser();
			Person person = parser.toPerson(data);
	
			parser.SavetoFile("output-Person.ttl");
	
			////SparqlEndPoint.insertTriple(parser.toString());
			////SparqlEndPoint.insertTriple(parser.toStringData());
			////SparqlEndPoint.insertTriple(parser.toStringData(),parser.toStringHeader());
			//SparqlEndPoint.insertTriple(parser.toStringData());
			person.Save();

			String response = parser.toString("TTL");
			return Response.ok(response, MediaType.TEXT_PLAIN).build();
				
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.toString()).build();		
		}

	}
	
	@GET
	@Path("/profiles/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person GetPerson(@PathParam("userid")String userid) {
		Person person = Person.getPerson(userid);
		return person;		
	}
	
	@PUT
	@Path("/profiles/{userid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person UpdatePerson(@QueryParam("userid")String userid) {
		return null;	
	}

	@GET
	@Path("/profiles/{userid}/applications")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String GetJobApplications(@QueryParam("userid")String userid) {
		
		String results = null;

		Person person = Person.getPerson(Person.prefix + userid);
		if (!(person.equals(null))) {
			results = "{}";//matching(job);
		}
		return results;

	}

	@POST
	@Path("/profiles/{userid}/applications/{jobid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person ApplyforJob(@QueryParam("jobid")String jobid, @QueryParam("userid")String userid) {
		return null;	
	}

	@DELETE
	@Path("/profiles/{userid}/applications/{jobid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JobPosting DeleteApplication(@QueryParam("jobid")String jobid, @QueryParam("userid")String userid) {
		return null;	
	}
}
