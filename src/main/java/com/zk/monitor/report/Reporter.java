package com.zk.monitor.report;

import java.util.Map;

/**
 * Reporter should be implemented by user.
 * For example report to Kafka or others.
 * 
 * @author justwin
 * @date 2016年9月13日 下午3:59:43
 * @version 1.0
 */
public interface Reporter {
	
	/**
	 * A hook method for init reporter
	 * 
	 * @param context
	 */
	public void init(Map<String, Object> context);

	/**
	 * report fetch result of zk
	 * 
	 * @param reportMap
	 * @return result of json
	 */
	public String report(Map<String, String> reportMap);
	
	public String getReporter();
}
