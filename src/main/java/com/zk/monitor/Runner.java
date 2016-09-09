package com.zk.monitor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.zk.monitor.conf.ZkMonitorConfigure;
import com.zk.monitor.utils.Logger;

public class Runner {

	private static Logger _logger;
	
	private List<Agent> zkMonitorAgents;
	private final ZkMonitorConfigure config;
	private int pollInterval = 60;
	private HashSet<AgentFactory> factories = new HashSet<AgentFactory>();
//	private Context context;
	
	public Runner() {
		super();
		
		zkMonitorAgents = new LinkedList<Agent>();
		
		config = new ZkMonitorConfigure();
	}
	
	
}
