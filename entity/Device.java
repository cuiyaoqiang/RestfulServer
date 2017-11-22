package entity;

public class Device {
	
	private int id;
	private String deviceIp;
	private int deviceStatus;

	public Device() {
	}

	public Device(int id, String deviceIp, int deviceStatus) {
		super();
		this.id = id;
		this.deviceIp = deviceIp;
		this.deviceStatus = deviceStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	public int getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", deviceIp=" + deviceIp + ", deviceStatus=" + deviceStatus + "]";
	}
	
}
