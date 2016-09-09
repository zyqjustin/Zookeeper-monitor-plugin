package com.zk.monitor;

import java.util.HashMap;
import java.util.Map;

import com.zk.monitor.metrics.MetricMeta;
import com.zk.monitor.processors.ZkFetchProcessor;
import com.zk.monitor.utils.Logger;

import static com.zk.monitor.utils.ZkConstants.*;

public class ZkAgent extends Agent {
	
	private static final Logger _logger = Logger.getLogger(ZkAgent.class);

	private final String name; // Agent name
	private final String host;
	private final int port;
	private String agentInfo;
	
    private final Map<String, MetricMeta> metricsMeta = new HashMap<String, MetricMeta>();
	private Map<String, Object> config = new HashMap<String, Object>();

	// fetch metric from zk
	private ZkFetchProcessor zkfetcher;
	
	private boolean isFirstReport = true;
	
	public ZkAgent(String name, String host, int port, Map<String, Object> config) {
		super();
		this.name = name;
		this.host = host;
		this.port = port;
		this.config = config;
		
		// connect zk
		zkfetcher = new ZkFetchProcessor(host, port);
		
		_logger.debug("Zookeeper agent initialized: ", formatAgentInfo(name, host, port, config));
	}

	/**
	 * 
	 * @param name Human name for agent
	 * @param host zk host
	 * @param port zk port
	 * @param config zk other config
	 * @return
	 */
	private Object formatAgentInfo(String name, String host, int port, Map<String, Object> config) {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ").append(name).append(" , ");
		sb.append("host: ").append(host).append(" , ");
		sb.append("port: ").append(port).append(" , ");
		sb.append("metrics: ").append(config).append(". ");
		return sb.toString();
	}

	/**
	 * This method is run for every poll cycle of the Agent. Get a zkconnection and gather metrics.
	 */
	@Override
	public void pollCycle() {
		if (isFirstReport) {
			String fetchConf = zkfetcher.fetch(COMMAND_CONF);
			String fetchEnvi = zkfetcher.fetch(COMMAND_ENVI);
		}
		String fetchRuok = zkfetcher.fetch(COMMAND_RUOK);
		String fetchStat = zkfetcher.fetch(COMMAND_STAT);
		String fetchMntr = zkfetcher.fetch(COMMAND_MNTR);
		
		
		
		isFirstReport = false;
	}

    private void addMetricMeta(String key, MetricMeta mm) {
        metricsMeta.put(key.toLowerCase(), mm);
    }
    
    private MetricMeta getMetricMeta(String key) {
    	if (!metricsMeta.containsKey(key.toLowerCase())) {
			addMetricMeta(key, new MetricMeta(true, "Operations/Second"));
		}
    	return metricsMeta.get(key.toLowerCase());
    }

	public Map<String, Object> getConfig() {
		return config;
	}

	@Override
	public String getAgentName() {
		return name;
	}
	
	
}
