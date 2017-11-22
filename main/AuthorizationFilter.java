package main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.internal.util.Base64;

/**
 * 对于request的过滤器
 * 过滤器主要是用来操纵请求和响应参数像HTTP头，URI和/或HTTP方法
 */
public class AuthorizationFilter implements ContainerRequestFilter {
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException { 
		
		final Charset CHARACTER_SET = Charset.forName("utf-8");  

		String authHeader = requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION); 
		if (authHeader != null && authHeader.startsWith("Basic")) {
			String decoded = new String(Base64.decode(authHeader.substring(6).getBytes()), CHARACTER_SET);  
			final String[] split = decoded.split(":");  
			final String username = split[0];  
			final String pwd = split[1];  
			if ("admin".equals(pwd)) { 
				requestContext.setSecurityContext(new SecurityContext() {  
					/* 返回一个java.security.Principal包含当前认证用户名称的对象。 
					 * 如果用户未经过身份验证，则返回null
					 * @see javax.ws.rs.core.SecurityContext#getUserPrincipal()
					 */
					@Override  
					public Principal getUserPrincipal() {  
						return new Principal() {  
							@Override  
							public String getName() {  
								return username;  
							}  
						};  
					}  

					/* 返回一个布尔值，指示经过身份验证的用户是否包含在指定的逻辑“角色”中。
					 * 如果用户没有通过身份验证，则返回该方法false。
					 * @see javax.ws.rs.core.SecurityContext#isUserInRole(java.lang.String)
					 */
					@Override  
					public boolean isUserInRole(String role) {  
						return true;  
					}  

					/* 返回一个布尔值，指示此请求是否使用安全通道（如HTTPS）进行。
					 * @see javax.ws.rs.core.SecurityContext#isSecure()
					 */
					@Override  
					public boolean isSecure() {  
						return false;  
					}  

					/* 返回用于保护资源的认证方案的字符串值。如果资源未通过身份验证，则返回空值。值与CGI变量AUTH_TYPE相同
					 * @see javax.ws.rs.core.SecurityContext#getAuthenticationScheme()
					 */
					@Override  
					public String getAuthenticationScheme() {  
						//BASIC_AUTH，FORM_AUTH，CLIENT_CERT_AUTH，DIGEST_AUTH（适合==比较）的静态成员之一或指示认证方案的容器特定的字符串之一;
						return "BASIC";  
					}  
				});  
				return;  
			}
		}  
		requestContext.abortWith(Response.status(401).header(HttpHeaders.WWW_AUTHENTICATE, "Basic").build());  

		//TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...  
		//logger.info(requestContext.getSecurityContext().getUserPrincipal().getName());  
	}
}