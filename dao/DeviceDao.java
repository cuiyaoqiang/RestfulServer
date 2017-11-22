package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import entity.Device;

public class DeviceDao {

	ConcurrentHashMap<String, Device> fakeDB = new ConcurrentHashMap<>();
	ConcurrentHashMap<Integer, Device> fakeDB2 = new ConcurrentHashMap<>();
	Device d1=new Device(1,"10.11.58.163",1);
	Device d2=new Device(2,"10.11.58.184",1);
	public DeviceDao() {
		fakeDB.put("10.11.58.163",d1 );
		fakeDB.put("10.11.58.184",d2 );

		fakeDB2.put(1, d1);
		fakeDB2.put(2, d2);
	}

	public Device getDevice(int id) {
		return fakeDB2.get(id);
	}
	public Device getDevice(String ip) {
		return fakeDB.get(ip);
	}

	public Device updateDevice(Device device) {

		String ip = device.getDeviceIp();
		fakeDB.put(ip, device);
		return fakeDB.get(ip);
	}

	/**新增设备
	 * @param device
	 * @return
	 */
	public int add(Device device) {
		// TODO Auto-generated method stub
		return 1;
	}

	public List<Device> getDevices() {
		// TODO Auto-generated method stub
		List<Device> devices=new ArrayList<>();
		devices.add(d1);
		devices.add(d2);
		return devices;
	}

	public int delete(int devId) {
		// TODO Auto-generated method stub
		return 1;
	}

	public int deleteByIp(String ip) {
		// TODO Auto-generated method stub
		return 2;
	}
}
