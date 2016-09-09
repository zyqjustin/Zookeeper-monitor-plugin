package com.zk.monitor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zk.monitor.exception.ConfigurationException;

import static com.zk.monitor.utils.ZkConstants.*;

public class ZkAgentFactory extends AgentFactory {

	@Override
	public Agent createConfiguredAgent(Map<String, Object> properties) throws ConfigurationException {
		String name = (String)properties.get("name");
		String host = (String)properties.get("host");
		String port = (String)properties.get("port");
		
		// TODO deal with absent value.
		
		return new ZkAgent(name, host, Integer.parseInt(port), readConfiguration());
	}

	public Map<String, Object> readConfiguration() {
		// TODO 
		return null;
	}

	Set<String> processMetrics(String metrics) {
		String[] ms = metrics.toLowerCase().split(COMMA);
		Set<String> set = new HashSet<String>(Arrays.asList(ms));
		set.remove(EMPTY_STRING);
		return set;
	}
	
}
