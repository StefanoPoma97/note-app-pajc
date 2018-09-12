package it.unibs.pajc.note.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {
	private String name;
	private String filename;

	public FileLogger(String logName, String filename) {
		this.name = logName;
		this.filename = filename;
	}

	public Logger get() {
		Logger lgr = Logger.getLogger(name);
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(Level.SEVERE);
		lgr.addHandler(ch);

		try {
			FileHandler fh = new FileHandler(filename, 1000000, 1);
			fh.setFormatter(new SimpleFormatter());
			fh.setLevel(Level.ALL);
			lgr.addHandler(fh);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to open logger");
		}
		return lgr;
	}

}
