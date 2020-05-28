package IP.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.commons.io.IOUtils;

import IP.GsonParser;
import IP.Matching;

import IP.Model.JobPosting;
import IP.Model.Skill;
import IP.Model.SparqlEndPoint;
import IP.config.Configuration;

public class IPServer {

    public static void main(String[] args) throws Exception {

        IPServer server = new IPServer();
        Configuration cnf = new Configuration();
		cnf.LoadConfiguration();
        server.start();
    }

    private void start() {        
        server.start();
        System.out.println("Server is running on port "+ port);
	}

	HttpServer server = null;
    int port = 8000;

    public IPServer() {

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);

            // server.createContext("/cvcreate", new cvcreateHandler());
            server.createContext("/test", new testHandler());            
            server.createContext("/jobpost", new jobpostHandler());
            server.createContext("/cv", new cvHandler());
            server.createContext("/jobmatching", new matchingHandler());

            // server.setExecutor(null); // creates a default executor
            server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool(/*5*/));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class testHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println(he.getRequestMethod() + " Request:Testing the server");

            writeResponse(he,"{OK}",200); 
        }
    }

    static class cvHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println(he.getRequestMethod() + ":CV parsing request");

            if (he.getRequestMethod().equalsIgnoreCase("POST")) {
                try {

                    // REQUEST Headers
                    // Headers requestHeaders = he.getRequestHeaders();
                    // Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();
                    // int contentLength =
                    // Integer.parseInt(requestHeaders.getFirst("Content-length"));

                    String data = getData(he);
 
                    GsonParser parser = new GsonParser();
                    parser.toCV(data);

                    parser.SavetoFile("output-cv.ttl");
                    String response = parser.toString();

                    writeResponse(he, response, HttpURLConnection.HTTP_OK);

                    System.out.println("CV Request Parsed");

                    SparqlEndPoint.insertTriple(parser.toStringData(),parser.toStringHeader());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class jobpostHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println(he.getRequestMethod() + ": Jobpost request");

            if (he.getRequestMethod().equalsIgnoreCase("POST")) {

                try {

                    // REQUEST Headers
                    Headers requestHeaders = he.getRequestHeaders();
                    // Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();
                    int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));

                    String data = getData(he); 

                    GsonParser parser = new GsonParser();
                    parser.toJobPosting(data);

                    parser.SavetoFile("output-jobpost.ttl");

                    String response = parser.toString("TTL");
                    System.out.println(response);

                    writeResponse(he, response, 200);
                    System.out.println("Jobpost Request Parsed");

                    //SparqlEndPoint.insertTriple(parser.toString());
                    //SparqlEndPoint.insertTriple(parser.toStringData());
                    //SparqlEndPoint.insertTriple(parser.toStringData(),parser.toStringHeader());
                    SparqlEndPoint.insertTriple(parser.toStringData());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        /*
         * public void handle(HttpExchange t) throws IOException {
         * 
         * //recived data
         * 
         * Map <String,String>parms =
         * IPServer.queryToMap(t.getRequestURI().getQuery());
         * 
         * //int UserId= Integer.parseInt(parms.get("userid")); String Username =
         * parms.get("userid");
         * 
         * String Rectype= parms.get("rectype");
         * 
         * //send response String response = "This is the response";
         * t.sendResponseHeaders(200, response.length()); OutputStream os =
         * t.getResponseBody(); os.write(response.getBytes()); os.close(); }
         */
    }

    static class matchingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println(he.getRequestMethod() + "Job matching request recived");

            Map<String, String> parms = IPServer.queryToMap(he.getRequestURI().getQuery());
            String Jobid = parms.get("jobid");
            JobPosting job = JobPosting.getJobPosting(JobPosting.prefix + Jobid);
            if (!(job.equals(null))) {
                String results = matching(job);
                writeResponse(he, results, 200);
            }
        }
    }

    /**
     * returns the url parameters in a map
     * 
     * @param query
     * @return map
     */
    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
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
            System.out.println("Cv with URI:" + uri + " has a score of: " + scores.get(uri) + ".");
            
            JsonObject jsonPropValue=new JsonObject();
            jsonPropValue.addProperty("name",uri);
            jsonPropValue.addProperty("role","");
            jsonPropValue.addProperty("available","");
            jsonPropValue.addProperty("expsalary","");
            jsonPropValue.addProperty("score",scores.get(uri));
            jsonResults.add(jsonPropValue);
        }

        return(jsonResults.toString());
    }

    private static void writeResponse(HttpExchange httpExchange, String response, int code) throws IOException {
        
        // RESPONSE Headers
        final Headers headers = httpExchange.getResponseHeaders();

        // Send RESPONSE Headers        
        headers.set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
        headers.add("Access-Control-Allow-Origin", "*");

        final byte[] rawResponseBody = response.getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(code, rawResponseBody.length);

        // RESPONSE Body
        httpExchange.getResponseBody().write(rawResponseBody);

        //httpExchange.sendResponseHeaders(code, response.length());
        //OutputStream os = httpExchange.getResponseBody();
        //os.write(response.getBytes());
        //os.close();        
    }

    private static String getData(HttpExchange httpExchange) {
        
        InputStream is = httpExchange.getRequestBody();
        String data = null;
        try {
            data = IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("data:" + data);
        return data;

    }
}