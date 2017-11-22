package main;

import java.io.IOException;
import java.net.URI;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author bh
 *
 */
public class Main {
	
	public static final String BASE_URI = "http://localhost:8081/";

	/**启动服务
	 * @return
	 */
	public static HttpServer startServer() {
		
		//服务器启动时会自动扫描该包下的所有类，根据该包中所含类的REST 资源路径的注解，在内存中做好映射
		final ResourceConfig rc = new ResourceConfig().packages("resource");
		//注册json转换器
		rc.register(JacksonJsonProvider.class);
		rc.register(ExceptionMapperSupport.class);
		
		rc.register(AuthorizationFilterFeature.class);
		rc.register(ResponseFilter.class);
		
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(Main.BASE_URI), rc);
	}

	public static void main(String[] args) throws IOException {
		
		final HttpServer server = Main.startServer();
		System.out.println(String.format("Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...", Main.BASE_URI));
		System.in.read();
		server.shutdownNow();
	}
}