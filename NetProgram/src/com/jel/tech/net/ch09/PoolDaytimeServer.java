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
/**
 * 线程池技术，这样不会被请求拖垮，
 * 请求越多可能会很慢，但是不会垮！
 * @author jelex.xu
 * @date 2017年9月15日
 */
public class PoolDaytimeServer {

	private static final int PORT = 1313;

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(50);

		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				Socket conn = server.accept();
				Callable<Void> task = new DaytimeTask(conn);
				pool.submit(task);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * running result:
		 * Jelex:~ zhenhua$ telnet localhost 1313
			Trying ::1...
			Connected to localhost.
			Escape character is '^]'.
			Fri Sep 15 21:07:27 CST 2017
			Connection closed by foreign host.
			Jelex:~ zhenhua$
		 */
	}

	private static class DaytimeTask implements Callable<Void> {

		private Socket conn;

		public DaytimeTask(Socket conn) {
			this.conn = conn;
		}

		@Override
		public Void call() throws Exception {
			try {
				Writer out = new OutputStreamWriter(conn.getOutputStream(), "ASCII");
				out.write(new Date() + "\r\n");
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
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
