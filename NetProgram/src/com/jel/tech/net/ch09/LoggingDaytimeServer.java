package com.jel.tech.net.ch09;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 带日志功能的时间服务器，
 * The general rule of thumb for error logs is that every line in
 * the error log should be looked at and resolved.
 * Do not follow the common antipattern of logging everything
 * you can think of just in case someone might need it someday
 * @author jelex.xu
 * @date 2017年9月16日
 */
public class LoggingDaytimeServer {

	public static final int PORT = 1313;

	private static final Logger auditLogger = Logger.getLogger("requests");
	private static final Logger errorLogger = Logger.getLogger("errors");

	public static void main(String[] args) {

		ExecutorService pool = Executors.newFixedThreadPool(50);

		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try {
					Socket conn = server.accept();
					Callable<Void> task = new LogDaytimeTask(conn);
					pool.submit(task);
				} catch (IOException e) {
					errorLogger.log(Level.SEVERE, "accept error", e);
				} catch(RuntimeException e) {
					errorLogger.log(Level.SEVERE, "unexpected error", e);
				}
			}
		} catch (IOException e) {
			errorLogger.log(Level.SEVERE, "Could not start the server.", e);
		} catch(RuntimeException e) {
			errorLogger.log(Level.SEVERE, "Could not start server " + e.getMessage(), e);
		}
		/*
		 * running result:
		 * Jelex:~ zhenhua$ telnet localhost 1313
			Trying ::1...
			Connected to localhost.
			Escape character is '^]'.
			Sat Sep 16 10:59:05 CST 2017
			Connection closed by foreign host.
			Jelex:~ zhenhua$

			控制台输出如下：
			九月 16, 2017 10:59:05 上午 com.jel.tech.net.ch09.LoggingDaytimeServer$LogDaytimeTask call
			信息: Sat Sep 16 10:59:05 CST 2017 connected from /0:0:0:0:0:0:0:1:49410
		 */
	}

	private static class LogDaytimeTask implements Callable<Void>{

		private Socket conn;

		public LogDaytimeTask(Socket conn) {
			this.conn = conn;
		}

		@Override
		public Void call() throws Exception {
			Date now = new Date();
			try {
				auditLogger.info(now + " " + "connected from " + conn.getRemoteSocketAddress());
				Writer out = new OutputStreamWriter(conn.getOutputStream(), "ASCII");
				out.write(now + "\r\n");
				out.flush();
			} catch (Exception e) {
				//client disconnected, ignore
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
			return null;
		}

	}
}
