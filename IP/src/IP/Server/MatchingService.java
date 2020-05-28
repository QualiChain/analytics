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

import IP.Matching;
import IP.Model.CV;
import IP.Model.JobPosting;
import IP.Model.Skill;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@Path("/")
public class MatchingService {
	
	public MatchingService() {
	}


	@GET
	@Path("/jobs/{jobid}/candidates")
	// @Path("/jobmatching")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String GetJobMatching(@PathParam("jobid")String jobid) {
		
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JobPosting ApplyforJob(@QueryParam("jobid")String jobid, @QueryParam("userid")String userid) {
		return null;	
	}

	@DELETE
	@Path("/jobs/{jobid}/apply/{userid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JobPosting DeleteApplication(@QueryParam("jobid")String jobid, @QueryParam("userid")String userid) {
		return null;	
	}
	
	private static String matching(JobPosting job) {
        System.out.println("Listing cv scores for the Job Post with URI: " + job.getURI() + " :");
        System.out.println("Job Requirements: ");
        List<Skill> reqs = job.getSkillReq();
        for (Skill req : reqs) {
            System.out.println("     " + req.getURI());
        }
        HashMap<String, Integer> scores = Matching.getAllCvMatches(job);

 
 
        JsonArray jsonResults=new JsonArray();

        Set<String> results = scores.keySet();
        for(String uri : results) {
			int score = scores.get(uri);
			//if (score > 0) {
				System.out.println("Cv with URI:" + uri + " has a score of: " + scores.get(uri) + ".");
				
				CV cv = CV.getCV(uri);
				String available = (cv.getApplication()==null) ? "" :  cv.getApplication().getAvailability();
				String expsalary = (cv.getApplication()==null) ? "" :  cv.getApplication().getExpectedSalary();

				JsonObject jsonPropValue=new JsonObject();
				jsonPropValue.addProperty("id",uri);
				jsonPropValue.addProperty("name",cv.getPersonURI());
				jsonPropValue.addProperty("role","");
				jsonPropValue.addProperty("available",available);
				jsonPropValue.addProperty("expsalary",expsalary);
				jsonPropValue.addProperty("score",score);
				jsonResults.add(jsonPropValue);
			//}
        }

        return(jsonResults.toString());
    }

}
