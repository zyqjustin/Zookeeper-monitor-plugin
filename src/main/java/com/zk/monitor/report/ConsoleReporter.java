package com.zk.monitor.report;

import java.util.Map;
import java.util.Map.Entry;

/**
 * for test
 * 
 * @author justwin
 * @date 2016年9月13日 下午5:52:27
 * @version 1.0
 */
public class ConsoleReporter implements Reporter {

	private int time = 0;

	/**
	 * A hook init method
	 */
	@Override
	public void init(Map<String, Object> context) {
		// init nothing
	}
	
	@Override
	public String report(Map<String, String> reportMap) {
		System.out.println("------------No. " + time + " start report------------");
		for (Entry<String, String> entry : reportMap.entrySet()) {
			System.out.println("report key: " + entry.getKey() + " => value: " + entry.getValue());
		}
		System.out.println("------------No. " + time + " end report------------");
		time++;
		return "";
	}

	@Override
	public String getReporter() {
		return "console reporter";
	}


}
