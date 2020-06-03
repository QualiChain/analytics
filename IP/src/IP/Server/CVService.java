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
import javax.ws.rs.core.Response;

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
	public Response CreateCV(String data) {
		System.out.println(data);
		GsonParser parser;
		try {
			parser = new GsonParser();
			CV cv = parser.toCV(data);

			parser.SavetoFile("output-cv.ttl");
			String response = parser.toString("TTL");

			////SparqlEndPoint.insertTriple(parser.toString());
			////SparqlEndPoint.insertTriple(parser.toStringData());
			////SparqlEndPoint.insertTriple(parser.toStringData(),parser.toStringHeader());
			//SparqlEndPoint.insertTriple(parser.toStringData());

			cv.Save();
			return Response.ok(response, MediaType.APPLICATION_JSON).build();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.toString()).build();		
		}
	}

	@GET
	@Path("/cv/{personURI}")
	@Produces(MediaType.APPLICATION_JSON)
	public CV GetCV(@PathParam("personURI")String personURI) {
		CV cv = CV.getCVbyPersonURI(personURI);	
		return cv;		
	}
	/*
	@GET
	@Path("/cv/{cvURI}")
	@Produces(MediaType.APPLICATION_JSON)
	public CV GetCV(@PathParam("cvURI")String cvURI) {
		CV cv = CV.getCV(cvURI);	
		return cv;		
	}
	*/
	@PUT
	@Path("/cv/{cvURI}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CV UpdateCV(@PathParam("cvURI")String cvURI) {
		return null;	
	}
	
	@DELETE
	@Path("/cv/{cvURI}")
	public void deleteCV(@PathParam("cvURI")String cvURI) {
		CV.deleteObject(cvURI);
	}

}
