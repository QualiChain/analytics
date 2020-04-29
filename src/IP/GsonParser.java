package IP;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.UUID;

import com.google.gson.Gson;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import IP.Model.*;

public class GsonParser {

    Gson g;
    String jobpostURI = "https://w3id.org/saro";
    String cvURI = "http://rdfs.org/resume-rdf/cv.rdfs";
    String QualiChainURI = "http://w3id.org/qualichain";

    private Model model;

    public GsonParser() {
        g = new Gson();
        this.model = ModelFactory.createDefaultModel();
        // model.read("D:/Google Drive/KnowledgeBiz/Qualichain/Ontology
        // Development/qualichain.ttl") ;

        this.model.setNsPrefix("saro", jobpostURI);
        this.model.setNsPrefix("cv", cvURI);
        this.model.setNsPrefix("qc", QualiChainURI);

    }

    public JobPosting toJobPosting(String data) {

        JobPosting p = g.fromJson(data, JobPosting.class);

        String uri = "job-" + UUID.randomUUID();
        //ModelData modelD = new ModelData();
        //modelD.addPropertyToResource(modelD, resURI, propURI, prop_value);
        p.setURI(uri);

        Resource jobpost = model.createResource(uri);

        jobpost.addProperty(RDF.type, model.createProperty(jobpostURI + "JobPosting"));
        // jobpost.addProperty(model.createProperty("rdfs:label"),p.getLabel());
        jobpost.addProperty(RDFS.label, p.getLabel());
        jobpost.addProperty(model.createProperty("rdfs:comment"), p.getJobDescription());
        jobpost.addProperty(model.createProperty(jobpostURI+"describes"), p.getJobDescription());

        
        for (Skill skill : p.getSkillReq()) {
            jobpost.addProperty(model.createProperty("saro:requiresSkill"),skill.getLabel());
            jobpost.addProperty(model.createProperty(QualiChainURI + "requiresExperience"), skill.getLabel());
        }
        
        System.out.println("Jobposting created:"+uri);
        return p;

    }

    public void toCV(String data) {
        
        CV p = g.fromJson(data, CV.class);

        //ModelData modelD = new ModelData();
        //modelD.addPropertyToResource(modelD, resURI, propURI, prop_value);

        Resource cv = model.createResource(cvURI + p.getLabel() + "-" + UUID.randomUUID());

        cv.addProperty(RDF.type, model.createProperty(cvURI + "cv"));
        // cv.addProperty(model.createProperty("rdfs:label"),p.getLabel());
        cv.addProperty(RDFS.label, p.getLabel());
        cv.addProperty(model.createProperty("rdfs:comment"), p.getDescription());

        for (Skill skill : p.getSkills()) {
            cv.addProperty(model.createProperty(QualiChainURI + "refersToAccomplishment"),skill.getLabel());
            cv.addProperty(model.createProperty(QualiChainURI + "refersToExperience"), skill.getLabel());
        }

    }

    public void SavetoFile(String filename) {
        // System.out.println("-------------------");
        // System.out.println(model);
        System.out.println("Saving to file----------");
        model.write(System.out, "TTL");
        System.out.println("-------------------");
        OutputStream out;
        try {
            out = new FileOutputStream(filename);
            model.write(out, "TTL");
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String toString() {
        return this.toString("NTRIPLES");    
    }

    public String toString(String type) {
        StringWriter out = new StringWriter();
        model.write(out, type);
        return out.toString();    
    }

    public String toStringData() {
        StringWriter out = new StringWriter();
        model.write(out, "TTL");
        out.getBuffer().delete(0, out.getBuffer().indexOf("\n\n")); 
        return out.toString();    
    }

    public String toStringHeader() {
        StringWriter out = new StringWriter();
        model.write(out, "TTL");
        out.getBuffer().delete(out.getBuffer().indexOf("\n\n"),out.getBuffer().length()-1); 
        String header = out.toString();
        //header = header.replaceAll("@", "");
        //header = header.replaceAll(".", "");
        return header;    
    }


    
}