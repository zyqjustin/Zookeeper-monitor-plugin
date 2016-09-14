package com.zk.monitor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.zk.monitor.conf.ZkMonitorConfigure;
import com.zk.monitor.exception.ConfigurationException;
import com.zk.monitor.utils.Logger;

public class Runner {

	private static Logger _logger;

	private List<Agent> zkMonitorAgents;
	private final ZkMonitorConfigure config;
	private int pollInterval = 6;
	private HashSet<AgentFactory> factories = new HashSet<AgentFactory>();

	public Runner() throws ConfigurationException {
		super();

		zkMonitorAgents = new LinkedList<Agent>();
		try {
			config = new ZkMonitorConfigure();
			config.init();

			Logger.init(config.getLogLevel(), 
					config.getLogFilePath(),
					config.getLogFileName(),
					config.getLogFileLimitInKilobytes());
		} catch (Exception e) {
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	public void setupAndRun() throws ConfigurationException {
		setupAgents();
		
		pollInterval = config.getGatherFrequency();
		
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(new PollAgentsRunnable(), 0, pollInterval, TimeUnit.SECONDS);
		
		_logger.info("***Zookeeper monitor started***");
		
		try {
			future.get();
		} catch (InterruptedException e) {
			_logger.error("SERVER: An error has occurred, error message: " + e.getMessage());
		} catch (ExecutionException e) {
			_logger.error("SERVER: An error has occurred, error message: " + e.getMessage());
		} finally {
			// clean up
			future.cancel(true);
			executor.shutdown();
		}
	}
	
	private void setupAgents() throws ConfigurationException {
		_logger.debug("Now setting up agents to be run");
	
		createAgents();
		
		Iterator<Agent> iterator = zkMonitorAgents.iterator();
		while (iterator.hasNext()) {
			Agent agent = iterator.next();
			
			agent.prepareToRun();
			agent.setupMetrics();
		}
	
	}
	
	private void createAgents() throws ConfigurationException {
		for (Iterator<AgentFactory> iterator = factories.iterator(); iterator.hasNext(); ) {
			AgentFactory factory = iterator.next();
			factory.createConfiguredAgents(this);
		}
	}

	/**
	 * Add an {@link AgentFactory} that can create {@link Agent}s
	 * 
	 * @param factory
	 */
	public void add(AgentFactory factory) {
		factories.add(factory);
	}
	
	/**
	 * Register an {@link Agent}
	 * 
	 * @param agent
	 */
	public void add(Agent agent) {
		zkMonitorAgents.add(agent);
	}
	
	private class PollAgentsRunnable implements Runnable {
		
		/**
		 * Harvest and report metric data.
		 */
		@Override
		public void run() {
			
			try {
				for (Iterator<Agent> iterator = zkMonitorAgents.iterator(); iterator.hasNext(); ) {
					Agent agent = iterator.next();
					agent.pollCycle();
				}
			} catch (Exception e) {
				_logger.error("SERVER: An error has occurred, error message: " + e.getMessage());
			}
			
		}
		
	}
}
