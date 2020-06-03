package IP.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

import IP.GsonParser;
import IP.Matching;
import IP.RDFBaseObjectClass;
import IP.Model.Application;
import IP.Model.CV;
import IP.Model.JobPosting;
import IP.Model.Person;
import IP.Model.RDFObject;
import IP.Model.Skill;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.jena.base.Sys;

@Path("/")
public class MatchingService {

	public MatchingService() {
	}

	@GET
	@Path("/jobs/{jobid}/candidates")
	// @Path("/jobmatching")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String GetJobMatching(@PathParam("jobid") String jobid) {

		System.out.println("Job matching request recived for " + jobid);
		String results = null;

		JobPosting job = JobPosting.getJobPosting(JobPosting.prefix + jobid);
		if (!(job.equals(null))) {
			results = matching(job);
		}
		return results;

	}

	@POST
	@Path("/jobs/{jobid}/apply/{userid}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response ApplyforJob(@PathParam("jobid") String jobid, @PathParam("userid") String userid, String data) {

		JobPosting job = JobPosting.getJobPosting(jobid);
		CV cv = CV.getCVbyPersonURI(Person.prefix + userid);

		GsonParser parser;
		try {

			parser = new GsonParser();
			Application application = parser.toApplication(data);
			application.setJobURI(job.getURI());
			application.setPersonURI(Person.prefix + userid);

			cv.setJobApplication(application);
			cv.Save();

			job.apply(application);
			job.Save();
			return Response.ok("Application created", MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.toString()).build();
		}

	}

	@DELETE
	@Path("/jobs/{jobid}/apply/{userid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void DeleteApplication(@PathParam("jobid") String jobid, @PathParam("userid") String userid) {

		try {
			CV cv = CV.getCVbyPersonURI(userid);
			cv.setJobApplication(null);
			cv.Save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String matching(JobPosting job) {
        System.out.println("Listing cv scores for the Job Post with URI: " + job.getURI() + " :");
        System.out.println("Job Requirements: ");
        List<Skill> reqs = job.getSkillReq();
        for (Skill req : reqs) {
            System.out.println("     " + req.getURI());
        }
        HashMap<String, Integer> scores = Matching.getAllCvMatches(job,false);

 
 
        JsonArray jsonResults=new JsonArray();

        Set<String> results = scores.keySet();
        for(String uri : results) {
			int score = scores.get(uri);
			//if (score > 0) {
				System.out.println("Cv with URI:" + uri + " has a score of: " + scores.get(uri) + ".");
				
				CV cv = CV.getCV(uri);
				String available = (cv.getApplication()==null) ? "" :  cv.getApplication().getAvailability();
				String expsalary = (cv.getApplication()==null) ? "" :  cv.getApplication().getExpectedSalary();


				//Person user = Person.getPersonByCV(uri);
				//String name = (user==null) ? "Noname" :  user.getName();
				String name = "Noname";

				JsonObject jsonPropValue=new JsonObject();
				jsonPropValue.addProperty("id",RDFObject.uri2id(cv.getPersonURI()));
				jsonPropValue.addProperty("cvid",uri);
				jsonPropValue.addProperty("name",name);
				jsonPropValue.addProperty("role",job.getLabel());
				jsonPropValue.addProperty("available",available);
				jsonPropValue.addProperty("expsalary",expsalary);
				jsonPropValue.addProperty("score",score);
				jsonResults.add(jsonPropValue);
			//}
        }

        return(jsonResults.toString());
    }

}
