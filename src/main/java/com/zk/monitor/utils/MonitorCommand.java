package com.zk.monitor.utils;

import com.zk.monitor.metrics.parse.ConfParser;
import com.zk.monitor.metrics.parse.ConsParser;
import com.zk.monitor.metrics.parse.EnviParser;
import com.zk.monitor.metrics.parse.MntrParser;
import com.zk.monitor.metrics.parse.Parser;
import com.zk.monitor.metrics.parse.RuokParser;
import com.zk.monitor.metrics.parse.SrvrParser;

public enum MonitorCommand {

	CONF(new ConfParser()),
	
	ENVI(new EnviParser()),
	
	CONS(new ConsParser()),
	
	SRVR(new SrvrParser()),
	
	RUOK(new RuokParser()),
	
	MNTR(new MntrParser());
	
	private Parser parser;
	private String command = this.toString().toLowerCase();
	
	private MonitorCommand(Parser parser) {
		this.parser = parser;
	}
	
	public String getCommand() {
		return command;
	}

	public Parser getParser() {
		return parser;
	}
	
}
