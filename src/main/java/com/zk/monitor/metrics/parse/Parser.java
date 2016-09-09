package com.zk.monitor.metrics.parse;

import java.util.Map;

public interface Parser {

	public Map<String, String> parse(String reply) throws Exception;
}
