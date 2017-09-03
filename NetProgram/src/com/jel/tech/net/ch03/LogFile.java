package com.jel.tech.net.ch03;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 * 这个LogFile被许多connection使用，问题就来了。。。
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class LogFile {

	private final Writer out;

	public LogFile(File f) throws IOException {
		this.out = new BufferedWriter(new FileWriter(f));
	}

	public void writeEntry(String msg) throws IOException {
		Date d = new Date();
		//下面4个write方法中间有可能被打断，被插足！
		//多线程访问嘛！你得理解，所以得上锁
		synchronized(out) {
			out.write(d.toString());
			out.write('\t');
			out.write(msg);
			out.write("\r\n");
		}
	}

	public void close() throws IOException {
		out.flush();
		out.close();
	}
}
