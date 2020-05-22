package IP.Server;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonRawValue;

import org.apache.jena.base.Sys;

import IP.GsonParser;
import IP.Model.JobPosting;
import IP.Model.SparqlEndPoint;

import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/")
public class JobpostingService {
	
	private static Logger Log = Logger.getLogger(JobpostingService.class.getName());
	public JobpostingService() {
		Log.setLevel( Level.FINER );
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String root() {
		return "OK";	
	}

	@GET
	@Path("/jobs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JobPosting> GetJobs() {
		return JobPosting.getJobPostings();	
	}

	@POST
	@Path("/jobs")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	//@Produces(MediaType.APPLICATION_JSON)
	//@JsonRawValue
	public Response CreateJobposting(String data) {
		Log.info(String.format("Post Request @ %s\n",  data));
		GsonParser parser;
		try {
			parser = new GsonParser();
			JobPosting job = parser.toJobPosting(data);
			parser.SavetoFile("output-jobpost.ttl");
			String response = parser.toString("TTL");

			job.Save();
			return Response.ok(response, MediaType.APPLICATION_JSON).build();

			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.toString()).build();		
		}

		////SparqlEndPoint.insertTriple(parser.toString());
		////SparqlEndPoint.insertTriple(parser.toStringData());
		////SparqlEndPoint.insertTriple(parser.toStringData(),parser.toStringHeader());
		//SparqlEndPoint.insertTriple(parser.toStringData());

	}

	@GET
	@Path("/jobs/{jobURI}")
	@Produces(MediaType.APPLICATION_JSON)
	public JobPosting GetJob(@PathParam("jobURI")String jobURI) {
		JobPosting job = JobPosting.getJobPosting(jobURI);
		return job;		
	}

	@PUT
	@Path("/jobs/{jobURI}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JobPosting UpdateJob(@PathParam("jobURI")String jobURI) {
		return null;	
	}
	
	@DELETE
	@Path("/jobs/{jobURI}")
	public void DeleteJob(@PathParam("jobURI")String jobURI) {
		JobPosting.deleteObject(jobURI); 
	}

}
