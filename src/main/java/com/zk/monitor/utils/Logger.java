package com.zk.monitor.utils;

import java.io.File;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;

public class Logger {

	public enum Level {
		Debug, Info, Warn, Error, Fatal;

		public static Level fromString(String value) {
			for (Level level : Level.values()) {
				if (level.toString().equalsIgnoreCase(value)) {
					return level;
				}
			}
			return null;
		}
	}
	
	// default configuration
	private static Level level = Level.Info;
	private static String filePath = "logs";
	private static String fileName = "zk_monitor.log";
	private static Integer fileLimitInKilobytes = 51200; // 25MB

	// logback configuration
	private static final String LogPattern = "[%date] %-5level %logger - %msg%n";
	private static final ConsoleAppender<ILoggingEvent> ConsoleAppender = new ConsoleAppender<ILoggingEvent>();
	private static final RollingFileAppender<ILoggingEvent> FileAppender = new RollingFileAppender<ILoggingEvent>();
	
	private final ch.qos.logback.classic.Logger logger;

	private Logger(ch.qos.logback.classic.Logger logger, Level level) {
		this.logger = logger;
		this.logger.addAppender(ConsoleAppender);
		this.logger.addAppender(FileAppender);
		this.logger.setLevel(translateLevel(level));
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return new Logger((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(clazz), getLevel());
	}

    public static void init(String logLevel, String logFilePath, String logFileName, Integer logFileLimitInKilobytes) {
        validateArgs(logLevel, logFilePath, logFileName, logFileLimitInKilobytes);
        
        Logger.level = Level.fromString(logLevel);
        Logger.filePath = logFilePath;
        Logger.fileName = logFileName;
        Logger.fileLimitInKilobytes = logFileLimitInKilobytes;
        
        initLogback();
    }
	
    private static void validateArgs(String logLevel, String logFilePath, String logFileName, Integer logFileLimitInKilobytes) {
        if (isNullOrEmptyString(logLevel)) {
            throw new IllegalArgumentException("'logLevel' must not be null or empty");
        }
        if (isNullOrEmptyString(logFilePath)) {
            throw new IllegalArgumentException("'logFilePath' must not be null or empty");
        }
        if (isNullOrEmptyString(logFileName)) {
            throw new IllegalArgumentException("'logFileName' must not be null or empty");
        }
        if (logFileLimitInKilobytes == null || logFileLimitInKilobytes < 0) {
            throw new IllegalArgumentException("'logFileLimitInKilobytes' must not be null or negative");
        }
    }
    
    private static boolean isNullOrEmptyString(String value) {
        return value == null || value.length() == 0;
    }
    
    private static void initLogback() {
        // reset logger context
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();
        
        // shared console appender
        ConsoleAppender.setContext(context);
        ConsoleAppender.setTarget("System.out");
        
        PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
        consoleEncoder.setContext(context);
        consoleEncoder.setPattern(LogPattern);
        consoleEncoder.start();
        ConsoleAppender.setEncoder(consoleEncoder);
        ConsoleAppender.start();
        
        // rolling file
        String logFile = getFilePath() + File.separatorChar + getFileName();
        FileAppender.setContext(context);
        FileAppender.setFile(logFile);
        
        // log pattern
        PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
        fileEncoder.setContext(context);
        fileEncoder.setPattern(LogPattern);
        fileEncoder.start();
        FileAppender.setEncoder(fileEncoder);
        
        // rolling policy
        FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(FileAppender);
        rollingPolicy.setFileNamePattern(logFile + "%i.zip");
        rollingPolicy.setMinIndex(1);
        rollingPolicy.setMaxIndex(1);
        rollingPolicy.start();
        
        // file max size - if fileLimit is 0, set max file size to maximum allowed
        long fileLimit = getFileLimitInKBytes() != 0 ? getFileLimitInKBytes() * 1024 : Long.MAX_VALUE;
        SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy = new SizeBasedTriggeringPolicy<ILoggingEvent>(String.valueOf(fileLimit));
        triggeringPolicy.start();
        
        FileAppender.setRollingPolicy(rollingPolicy);
        FileAppender.setTriggeringPolicy(triggeringPolicy);
        FileAppender.start();
    }
    
    public void debug(Object... messages) {
        if (logger.isDebugEnabled()) {
            logger.debug(buildMessage(messages));
        }
    }
    
    public void debug(Throwable throwable, Object... messages) {
        if (logger.isDebugEnabled()) {
            logger.debug(buildMessage(messages), throwable);
        }
    }
    
    public void info(Object... messages) {
        if (logger.isInfoEnabled()) {
            logger.info(buildMessage(messages));
        }
    }
    
    public void info(Throwable throwable, Object... messages) {
        if (logger.isInfoEnabled()) {
            logger.info(buildMessage(messages), throwable);
        }
    }
    
    public void warn(Object... messages) {
        if (logger.isWarnEnabled()) {
            logger.warn(buildMessage(messages));
        }
    }
    
    public void warn(Throwable throwable, Object... messages) {
        if (logger.isWarnEnabled()) {
            logger.warn(buildMessage(messages), throwable);
        }
    }
    
    public void error(Object... messages) {
        if (logger.isErrorEnabled()) {
            logger.error(buildMessage(messages));
        }
    }
    
    public void error(Throwable throwable, Object... messages) {
        if (logger.isErrorEnabled()) {
            logger.error(buildMessage(messages), throwable);
        }
    }
    
    public void fatal(Object... messages) {
        error(messages);
    }
    
    public void fatal(Throwable throwable, Object... messages) {
        error(throwable, messages);
    }
    
    // getters
    static Level getLevel() {
        return level;
    }
    
    static String getFilePath() {
        return filePath;
    }
    
    static String getFileName() {
        return fileName;
    }
    
    static Integer getFileLimitInKBytes() {
        return fileLimitInKilobytes;
    }
    
    static String buildMessage(Object... messages) {
        if (messages == null) {
            throw new IllegalArgumentException("'messages' cannot be null");
        }
        
        StringBuilder builder = new StringBuilder();
        for (Object message : messages) {
            builder.append(message);
        }
        return builder.toString();
    }
    
    /*
     * Translate supported log level to Logback level
     */
    static ch.qos.logback.classic.Level translateLevel(Level level) {
        if (level == null) {
            throw new IllegalArgumentException("'level' cannot be null");
        }
        
        switch (level) {
        case Debug:
            return ch.qos.logback.classic.Level.DEBUG;
        case Info:
            return ch.qos.logback.classic.Level.INFO;
        case Warn:
            return ch.qos.logback.classic.Level.WARN;
        case Error:
            return ch.qos.logback.classic.Level.ERROR;
        case Fatal:
            return ch.qos.logback.classic.Level.ERROR;
        default:
            return ch.qos.logback.classic.Level.INFO;
        }
    }
}
