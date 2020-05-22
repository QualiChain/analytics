package IP;

public class ServerLaunch {
	
	/*
	 * Launch necessary servers for QualiChain project, one server per thread
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//TODO: setup fuseki server thread
		Thread fuseki = new Thread(() -> {
			
		});
		//TODO: setup Sorl server thread
//		Thread Solr = new Thread(() -> {
//			
//		});

		while(true) {
			if(fuseki.isAlive())
				System.out.println(fuseki.getState());
			else
				break;
		}
		System.err.println("------------------Servers shut down------------------");
	}

}
