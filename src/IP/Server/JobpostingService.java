package IP.Server;

import java.util.List;

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

import org.apache.jena.base.Sys;

import IP.GsonParser;
import IP.Model.JobPosting;
import IP.Model.SparqlEndPoint;


@Path("/")
public class JobpostingService {
	
	public JobpostingService() {
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
	//@Path("/jobs")
	@Path("/jobpost")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String CreateJobposting(String data) {
		GsonParser parser = new GsonParser();
		JobPosting job = parser.toJobPosting(data);

		parser.SavetoFile("output-jobpost.ttl");

		job.Save();
		////SparqlEndPoint.insertTriple(parser.toString());
		////SparqlEndPoint.insertTriple(parser.toStringData());
		////SparqlEndPoint.insertTriple(parser.toStringData(),parser.toStringHeader());
		//SparqlEndPoint.insertTriple(parser.toStringData());

		String response = parser.toString("TTL");
		return response;

	}

	@GET
	@Path("/jobs/{jobid}")
	@Produces(MediaType.APPLICATION_JSON)
	public JobPosting GetJob(@PathParam("jobid")String jobid) {
		JobPosting job = JobPosting.getJobPosting(jobid);
		return job;		
	}

	@PUT
	@Path("/jobs/{jobid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JobPosting UpdateJob(@QueryParam("jobid")String jobid) {
		return null;	
	}	
}
