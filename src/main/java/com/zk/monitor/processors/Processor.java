package com.zk.monitor.processors;

public interface Processor {

	/**
	 * Process a {@code Number} for metric reporting.
	 * @param val the Number to be processed
	 * @return Number the processed Number
	 */
	public Number process(Number val);
}
