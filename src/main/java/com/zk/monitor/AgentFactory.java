package com.zk.monitor;

import java.util.Map;

import com.zk.monitor.exception.ConfigurationException;
import com.zk.monitor.utils.Logger;

public abstract class AgentFactory {

	private static final Logger _logger = Logger.getLogger(AgentFactory.class);
	
	public abstract Agent createConfiguredAgent(Map<String, Object> properties) throws ConfigurationException;
	
	void createConfiguredAgents(Runner runner) throws ConfigurationException {
		// TODO create agent and register in runner
		
	}
}
