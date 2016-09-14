package com.zk.monitor;

import static com.zk.monitor.utils.MonitorCommand.CONF;
import static com.zk.monitor.utils.MonitorCommand.CONS;
import static com.zk.monitor.utils.MonitorCommand.ENVI;
import static com.zk.monitor.utils.MonitorCommand.MNTR;
import static com.zk.monitor.utils.MonitorCommand.RUOK;
import static com.zk.monitor.utils.MonitorCommand.SRVR;

import java.util.HashMap;
import java.util.Map;

import com.zk.monitor.metrics.MetricMeta;
import com.zk.monitor.processors.ZkFetchProcessor;
import com.zk.monitor.report.Reporter;
import com.zk.monitor.utils.Logger;

public class ZkAgent extends Agent {
	
	private static final Logger _logger = Logger.getLogger(ZkAgent.class);

	private final String name; // Agent name
	private final String host;
	private final int port;
	
    private final Map<String, MetricMeta> metricsMeta = new HashMap<String, MetricMeta>();
	private Map<String, Object> config = new HashMap<String, Object>();

	// fetch metric from zk
	private ZkFetchProcessor zkfetcher;
	private Reporter reporter;
	
	private boolean isFirstReport = true;
	
	public ZkAgent(String name, String host, int port, Map<String, Object> config, Reporter reporter) {
		super();
		this.name = name;
		this.host = host;
		this.port = port;
		this.config = config;
		this.reporter = reporter;
		
		// connect zk
		zkfetcher = new ZkFetchProcessor(host, port);
		
		_logger.debug("Zookeeper agent initialized: ", formatAgentInfo(name, host, port, config, reporter));
	}

	/**
	 * 
	 * @param name Human name for agent
	 * @param host zk host
	 * @param port zk port
	 * @param config zk other config
	 * @param reporter reporter where
	 * @return
	 */
	private Object formatAgentInfo(String name, String host, int port, Map<String, Object> config, Reporter reporter) {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ").append(name).append(" , ");
		sb.append("host: ").append(host).append(" , ");
		sb.append("port: ").append(port).append(" , ");
		sb.append("metrics: ").append(config).append(" , ");
		sb.append("reporter: ").append(reporter.getReporter()).append(" .");
		return sb.toString();
	}

	@Override
	public void prepareToRun() {
		super.prepareToRun();
		
		reporter.init(config);
	}
	
	/**
	 * This method is run for every poll cycle of the Agent. Get a zkconnection and gather metrics.
	 */
	@Override
	public void pollCycle() {
		Map<String, String> reportMap = new HashMap<String, String>();
		try {
			if (isFirstReport) {
				reportMap.putAll(CONF.getParser().parse(zkfetcher.fetch(CONF.getCommand())));
				reportMap.putAll(ENVI.getParser().parse(zkfetcher.fetch(ENVI.getCommand())));
			}
			reportMap.putAll(RUOK.getParser().parse(zkfetcher.fetch(RUOK.getCommand())));
			reportMap.putAll(CONS.getParser().parse(zkfetcher.fetch(CONS.getCommand())));
			reportMap.putAll(MNTR.getParser().parse(zkfetcher.fetch(MNTR.getCommand())));
			reportMap.putAll(SRVR.getParser().parse(zkfetcher.fetch(SRVR.getCommand())));
		} catch (Exception e) {
			_logger.error("Fetch zk report monitor, error: ", e.getMessage());
			return;
		}
		
		reporter.report(reportMap);
		
		isFirstReport = false;
	}

    private void addMetricMeta(String key, MetricMeta mm) {
        metricsMeta.put(key.toLowerCase(), mm);
    }
    
    // TODO unuse
    private MetricMeta getMetricMeta(String key) {
    	if (!metricsMeta.containsKey(key.toLowerCase())) {
			addMetricMeta(key, new MetricMeta(true, "Operations/Second"));
		}
    	return metricsMeta.get(key.toLowerCase());
    }

	public Map<String, Object> getConfig() {
		return config;
	}

	public Reporter getReporter() {
		return reporter;
	}

	@Override
	public String getAgentName() {
		return name;
	}
	
	
}
