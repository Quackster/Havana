package org.alexdev.http.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	public static Logger getErrorLogger() {
		return SimpleLog.of("ErrorLogger");
	}
}
