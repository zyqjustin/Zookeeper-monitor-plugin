package com.zk.monitor.conf;

public class ZkMonitorConfigure {

	@ConfigableField(name = "zk.monitor.name", required = false, defaultValue = "zk-monitor")
	private String zkMonitorName;
	
	@ConfigableField(name = "zk.hosts")
	private String zkHosts;
	
	@ConfigableField(name = "zk.gather.frequency.minutes", required = false, defaultValue = "1")
	private int gatherFrequency;

	public ZkMonitorConfigure() {
		// do nothing
	}

	// TODO configure report kafka
	
	// setters and getters
	public String getZkMonitorName() {
		return zkMonitorName;
	}

	public void setZkMonitorName(String zkMonitorName) {
		this.zkMonitorName = zkMonitorName;
	}

	public String getZkHosts() {
		return zkHosts;
	}

	public void setZkHosts(String zkHosts) {
		this.zkHosts = zkHosts;
	}

	public int getGatherFrequency() {
		return gatherFrequency;
	}

	public void setGatherFrequency(int gatherFrequency) {
		this.gatherFrequency = gatherFrequency;
	}
	
}
