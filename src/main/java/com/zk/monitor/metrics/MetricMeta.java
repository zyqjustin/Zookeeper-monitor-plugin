package com.zk.monitor.metrics;

import com.zk.monitor.processors.EpochProcessor;
import static com.zk.monitor.utils.ZkConstants.*;

public class MetricMeta {

	public final static String DEFAULT_UNIT = "Operations";
	public final static String DEFAULT_COUNTER_UNIT = DEFAULT_UNIT + "/Second";
	
	private final String unit;
	private EpochProcessor counter = null;
	
	public MetricMeta(boolean isCounter, String unit) {
		this.unit = unit;
		if (isCounter) {
			this.counter = new EpochProcessor();
		}
	}

	public MetricMeta(boolean isCounter) {
		this.unit = isCounter ? DEFAULT_COUNTER_UNIT : DEFAULT_UNIT;
		if (isCounter) {
			this.counter = new EpochProcessor();
		}
	}
	
	public static MetricMeta defaultMetricMeta() {
		return new MetricMeta(true);
	}
	
	public boolean isCounter() {
		return (this.counter == null ? false : true);
	}
	
	public String getUnit() {
		return unit;
	}

	public EpochProcessor getCounter() {
		return counter;
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append(isCounter() ? COUNTER : EMPTY_STRING)
			.append(LEFT_PAREN)
			.append(getUnit())
			.append(RIGHT_PAREN)
			.toString();
	}
	
}
