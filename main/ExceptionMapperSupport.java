package main;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;



/**
 * 统一异常处理器
 */
@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {


	@Context
	private HttpServletRequest request;

	@Context
	private ServletContext servletContext;

	/**
	 * 异常处理
	 * 
	 * @param exception
	 * @return 异常处理后的Response对象
	 */
	public Response toResponse(Exception exception) {
		
		Status statusCode = Status.INTERNAL_SERVER_ERROR;
		System.out.println(exception.getMessage()+"  pp[[");
		return Response.ok("wrong", MediaType.TEXT_PLAIN).status(statusCode)
				.build();
	}
}
