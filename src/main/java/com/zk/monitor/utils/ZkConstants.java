package com.zk.monitor.utils;

public class ZkConstants {
	
	/*------------- Basic -------------*/
	public static final String TAB = "\t";
    public static final String COMMA = ",";
    public static final String SLASH = "/";
    public static final String SPACE = " ";
    public static final String POINT = ".";
    public static final String COLON = ":";
    public static final String EMPTY_STRING = "";
    public static final String UNDERSCORE = "_";
    public static final String LEFT_PAREN = "(";
    public static final String RIGHT_PAREN = ")";
    public static final String ARROW = "->";
    public static final String EQUALS = "=";
    public static final String NEW_LINE = "\n"; 
    public static final String RESULT = "result";
    public static final String COUNTER = "[counter]";
    public static final String METRIC_LOG_PREFIX = "Metric ";

    /*------------- Zookeeper command -------------*/
    public static final String COMMAND_CONF = "conf";
    public static final String COMMAND_ENVI = "envi";
    public static final String COMMAND_SRVR = "srvr";
    public static final String COMMAND_CONS = "cons";
    public static final String COMMAND_RUOK = "ruok";
    public static final String COMMAND_STAT = "stat"; // abandon, see COMMAND_SRVR and COMMAND_CONS
    public static final String COMMAND_MNTR = "mntr";
    
    /*------------- Zookeeper Configuration -------------*/
    public static final String CONF_ZK_HOSTS        = "zk.hosts";
    public static final String CONF_ZK_MONITOR_NAME = "zk.monitor.name";
    public static final String CONF_ZK_GATHER_FREQUENCY_MINUTES = "zk.gather.frequency.minutes";
    public static final String CONF_ZK_LOG_LEVEL     = "log.level";
    public static final String CONF_ZK_LOG_FILE_PATH = "log.file.path";
    public static final String CONF_ZK_LOG_FILE_NAME = "log.file.name";
    public static final String CONF_ZK_LOG_FILE_LIMIT_KB = "log.file.limit.kb";
    
    /*------------- Zookeeper command -------------*/
    public static final String METRIC_STATUS_SERVERING = "status/servering";
}
