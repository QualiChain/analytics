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

import IP.GsonParser;
import IP.Model.CV;
import IP.Model.SparqlEndPoint;

@Path("/")
public class CVService {
	
	public CVService() {
	}

    @GET
	@Path("/cv")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CV> GetCVs() {
		return CV.getCVs();	
	}

	@POST
	@Path("/cv")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String CreateCV(String data) {
		GsonParser parser = new GsonParser();
		parser.toCV(data);

		parser.SavetoFile("output-cv.ttl");

		//SparqlEndPoint.insertTriple(parser.toString());
		//SparqlEndPoint.insertTriple(parser.toStringData());
		//SparqlEndPoint.insertTriple(parser.toStringData(),parser.toStringHeader());
		SparqlEndPoint.insertTriple(parser.toStringData());

		String response = parser.toString("TTL");
		return response;

	}

	@GET
	@Path("/cv/{cvid}")
	@Produces(MediaType.APPLICATION_JSON)
	public CV GetCV(@PathParam("cvid")String cvid) {
		CV cv = CV.getCV(cvid);
		return cv;		
	}

	@PUT
	@Path("/cv/{cvid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CV UpdateCV(@QueryParam("cvid")String cvid) {
		return null;	
	}

}
