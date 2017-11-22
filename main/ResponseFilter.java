package main;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;


/**
 * 对于response的过滤器
 * 过滤器主要是用来操纵请求和响应参数像HTTP头，URI和/或HTTP方法
 * @date 2016/7/27
 */
@Provider
public class ResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext creq,
                       ContainerResponseContext cres) throws IOException {
        /**
         * 具体可以获取什么参数,加个断点就可以看到了
         */
        System.out.println("执行回复过滤");

        cres.getHeaders().add("Access-Control-Allow-Origin", "*");
        /**
         * 允许的Header值，不支持通配符
         */
        cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");

        /**
         * 即使只用其中几种，header和options是不能删除的，因为浏览器通过options请求来获取服务的跨域策略
         */
        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        /**
         * CORS策略的缓存时间
         */
        cres.getHeaders().add("Access-Control-Max-Age", "1209600");

       //可以通过 throw new WebApplicationException(Status.UNAUTHORIZED); 来中断请求

    
    }
}