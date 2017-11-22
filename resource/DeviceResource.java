package resource;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.map.ObjectMapper;

import dao.DeviceDao;
import entity.Device;
import main.AuthAnnotation;

@Path("device")
public class DeviceResource {


	@Context UriInfo uriInfo;  
	@Context HttpHeaders httpHeaders;  
	@Context SecurityContext sc;  
	@Context Request req;  
	@Context Response resp;  
	@Context HttpServletResponse response;  
	@Context HttpServletRequest request;  
	
	//注入 Dao 实例
	private final DeviceDao deviceDao;
	

	public DeviceResource() {

		deviceDao = new DeviceDao();
	}

	/**获取所有资源
	 * @param deviceIp
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> getAll() {

		System.out.println("uriInfo"+uriInfo);
		System.out.println("httpHeaders"+httpHeaders);
		System.out.println("sc"+sc);
		System.out.println("req"+req);
		System.out.println("resp"+resp);
		System.out.println("response"+response);
		System.out.println("request"+request);
		
		
		List<Device> devices = null;
		devices = deviceDao.getDevices();
		return devices;
	}

	/**根据路径获取资源
	 * @param deviceIp
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id:[0-9]*}")
	public Device getByPath(@PathParam("id") final int id) {

		Device result = null;
		if (id != 0) {
			result = deviceDao.getDevice(id);
		}
		return result;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{dest}/{id}")
	public Device getByPath1(@PathParam("dest") String dest,@PathParam("id") int id) {

		System.out.println(dest+"  "+id);
		Device result = null;
		if (id != 0) {
			result = deviceDao.getDevice(id);
		}
		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{dest}/{id},{name}")
	public Device getByPath2(@PathParam("dest") String dest,@PathParam("id") int id,@PathParam("name") String name) {

		System.out.println(dest+"  "+id+"  "+name);
		Device result = null;
		if (id != 0) {
			result = deviceDao.getDevice(id);
		}
		return result;
	}

	/** 传递json参数
	 * @param param
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{param}")
	public Device getByPath3(@PathParam("param") final String param) {

		ObjectMapper mapper = new ObjectMapper();  
		Device result = null;
		try {
			result=mapper.readValue(param, Device.class);
			result.setDeviceIp("192.163.20.79");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	/**根据查询条件获取资源
	 * @param deviceIp
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dev") 
	public Device get(@QueryParam("ip") final String deviceIp) {

		Device result = null;
		if (deviceIp != null) {
			result = deviceDao.getDevice(deviceIp);
		}
		return result;
	}
	/**新增资源
	 * @param deviceIp
	 * @return
	 */
	@AuthAnnotation
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public int post(Device device) {

		int result=0;
		if (device != null) {
			result = deviceDao.add(device);
		}
		return result;
	}
	@Path("{dest}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public int post2(@PathParam("dest")String dest,Device device) {

		System.out.println(1/0);
		System.out.println(dest+"  post2  "+device);
		int result=0;
		if (device != null) {
			result = deviceDao.add(device);
		}
		return result;
	}

	/**删除资源
	 * @param deviceIp
	 * @return
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id:[0-9]*}")
	public int delete(@PathParam("id") int devId) {

		int result=0;
		if (devId != 0) {
			result = deviceDao.delete(devId);
		}
		return result;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{dest}/{id:[0-9]*}")
	public int delete2(@PathParam("dest") String dest,@PathParam("id") int devId) {

		System.out.println(dest+"  delete2  "+ devId);
		int result=0;
		if (devId != 0) {
			result = deviceDao.delete(devId);
		}
		return result;
	}
	/**修改资源
	 * @param device
	 * @return
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Device put(Device device) {

		Device result = null;
		if (device != null) {
			result = deviceDao.updateDevice(device);
		}
		return result;
	}
	/**修改资源
	 * @param device
	 * @return
	 */
	@Path("{dest}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Device put2(@PathParam("dest")String dest,Device device) {

		System.out.println(dest+" put2 "+device);
		Device result = null;
		if (device != null) {
			result = deviceDao.updateDevice(device);
		}
		return result;
	}
}