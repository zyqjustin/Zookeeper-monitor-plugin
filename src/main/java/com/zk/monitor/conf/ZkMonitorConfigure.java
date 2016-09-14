package com.zk.monitor.conf;

import static com.zk.monitor.utils.ZkConstants.CONF_ZK_GATHER_FREQUENCY_MINUTES;
import static com.zk.monitor.utils.ZkConstants.CONF_ZK_HOSTS;
import static com.zk.monitor.utils.ZkConstants.CONF_ZK_LOG_FILE_LIMIT_KB;
import static com.zk.monitor.utils.ZkConstants.CONF_ZK_LOG_FILE_NAME;
import static com.zk.monitor.utils.ZkConstants.CONF_ZK_LOG_FILE_PATH;
import static com.zk.monitor.utils.ZkConstants.CONF_ZK_LOG_LEVEL;
import static com.zk.monitor.utils.ZkConstants.CONF_ZK_MONITOR_NAME;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ZkMonitorConfigure {

	@ConfigableField(name = CONF_ZK_MONITOR_NAME, required = false, defaultValue = "zk-monitor")
	private String zkMonitorName;
	
	@ConfigableField(name = CONF_ZK_HOSTS)
	private String zkHosts;
	
	@ConfigableField(name = CONF_ZK_GATHER_FREQUENCY_MINUTES, required = false, defaultValue = "1")
	private int gatherFrequency; // unuseful
	
	@ConfigableField(name = CONF_ZK_LOG_LEVEL, required = false, defaultValue = "info")
	private String logLevel;

	@ConfigableField(name = CONF_ZK_LOG_FILE_PATH, required = false, defaultValue = "logs")
	private String logFilePath;
	
	@ConfigableField(name = CONF_ZK_LOG_FILE_NAME, required = false, defaultValue = "zk_monitor.log")
	private String logFileName;
	
	@ConfigableField(name = CONF_ZK_LOG_FILE_LIMIT_KB, required = false, defaultValue = "51200")
	private int logFileLimitInKilobytes;
	
	// TODO add other config, like report
	
	public ZkMonitorConfigure() {
		// do nothing
	}
	
	public void init() {
		Field[] declaredFields = this.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
			for
		}
	}

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

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public Integer getLogFileLimitInKilobytes() {
		return logFileLimitInKilobytes;
	}

	public void setLogFileLimitInKilobytes(Integer logFileLimitInKilobytes) {
		this.logFileLimitInKilobytes = logFileLimitInKilobytes;
	}
	
}
