package com.zk.monitor.metrics.parse;

import java.util.HashMap;
import java.util.Map;
import static com.zk.monitor.utils.ZkConstants.*;

/**
 * execute command: <br>
 *     echo ruok | nc localhost 2181 <br>
 * 
 * return like: <br>
 *     imok   // if correct <br>
 * 
 * @author justwin
 * @date 2016年9月9日 下午4:41:20
 * @version 1.0
 */
public class RuokParser implements Parser {

	private static final String CORRECT_RET = "imok";
	
	@Override
	public Map<String, String> parse(String reply) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		
		String ret = reply.trim();
		if (CORRECT_RET.equals(ret)) {
			retMap.put(METRIC_STATUS_SERVERING, "0");
		} else {
			retMap.put(METRIC_STATUS_SERVERING, "1");
		}
		return retMap;
	}

}
