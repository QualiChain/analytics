package IP;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Resource;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFSubject;

public class RDFBaseObjectClass {

    protected String URI;
    protected String ID;
    protected String Label;
    protected String Comment;
    
    @RDFSubject
    public String getURI()	{   
        return URI;
    }
    
    public void setURI(String uri) {
    	this.URI = uri;
    }

    public String getId()	{ 
        return ID;
    }
    public void setId(String id) {  
         this.ID = id;
    }

    @RDF("rdfs:label")
    public String getLabel()	{ 
        return Label;
    }
    
    public void setLabel(String label) {   
        this.Label = label;  
    }
    
    public String getComment() {
    	return Comment;
    }
    
    public void setComment(String comment) {
    	this.Comment = comment;
    }
    
}
