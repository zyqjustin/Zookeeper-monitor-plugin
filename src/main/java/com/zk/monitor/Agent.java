package com.zk.monitor;

import com.zk.monitor.metrics.DataCollector;
import com.zk.monitor.utils.Logger;

public abstract class Agent {
	
	private static final Logger _logger = Logger.getLogger(Agent.class);

	private static final String REPORTING_METRIC_MSG = "Reporting metric: ";
	
	private final DataCollector collector;

	public Agent() {
		super();
		this.collector = new DataCollector();
	}
	
    /**
     * The {@code Agent} will gather and report metrics from this method during every poll cycle.
     * It is called by the {@link Runner} at a set interval and is run in a loop that never returns.
     * <p> This method must be overridden by subclasses of {@code Agent}.
     */
	public abstract void pollCycle();
	
    /**
     * A human readable label for the component that this {@code Agent} is reporting metrics on.
     * <p> This method must be overridden by subclasses of {@code Agent}.
     * @return A name representing an instance of an Agent
     */
	public abstract String getAgentName();
	
	/**
	 * A hook called when the {@code Agent} is setup.
	 * Subclasses may override but must call {@code super}.
	 */
	public void setupMetrics() {
		_logger.debug("Now setting up metrics.");
	}

	/**
	 * A hook called when the {@code Agent} is setup.
	 * Subclasses may override but must call {@code super}.
	 */
	public void prepareToRun() {
		_logger.debug("Preparing to run.");
	}
	
	public void reportMetric(String metricName, String units, Number value) {
		if (value != null) {
			_logger.debug(REPORTING_METRIC_MSG, metricName);
			collector.addData(metricName, units, value);
		}
	}
	
	public void reportMetric(String metricName, String units, int count, Number value, Number minValue, Number maxValue, Number sumOfSquares) {
		if (value != null && minValue != null && maxValue != null && sumOfSquares != null) {
			_logger.debug(REPORTING_METRIC_MSG, metricName);
			collector.addData(metricName, units, count, value, minValue, maxValue, sumOfSquares);
		}
	}

	public DataCollector getCollector() {
		return collector;
	}
	
}
