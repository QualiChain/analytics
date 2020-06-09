package IP.Server;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import IP.config.Configuration;
import Utils.IP;

public class RestServer {
	
	private static Logger Log = Logger.getLogger(JobpostingService.class.getName());

	static {
		System.setProperty("java.net.preferIPv4Stack", "true");
		System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s");
	}
	
	public static final int PORT = 7777;
	public static final String SERVICE = "Matching";
	public static String SERVER_BASE_URI = "http://%s:%s/";
	
	public static void main(String[] args) throws Exception {

		Configuration cnf=new Configuration();
		cnf.LoadConfiguration();

		Log.setLevel( Level.FINER );

		String ip = IP.hostAddress();
		String serverURI = String.format(SERVER_BASE_URI, ip, Configuration.port);
		
		ResourceConfig config = new ResourceConfig();

		config.register(CORSResponseFilter.class);
		//config.register(new PersonService()); 
		config.register(new CVService()); 
		config.register(new JobpostingService()); 
		config.register(new MatchingService()); 

		
		JdkHttpServerFactory.createHttpServer( URI.create(serverURI.replace(ip, "0.0.0.0")), config);
		Log.info(String.format("%s Server ready @ %s\n",  SERVICE, serverURI));
	}	
}
