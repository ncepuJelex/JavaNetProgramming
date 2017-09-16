package com.jel.tech.net.ch09;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
/**
 * 多线程的时间服务器，
 * 每来一个查询，就去创建一个线程去完成服务，这样迟早会被请求拖垮……
 * @author jelex.xu
 * @date 2017年9月15日
 */
public class MultiThreadedDaytimeServer {

	public static final int PORT = 1313;

	public static void main(String[] args) {

		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try {
					Socket s = server.accept();
					DaytimeThread task = new DaytimeThread(s);
					task.start();
				} catch (Exception e) {
				}
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
			Fri Sep 15 20:56:58 CST 2017
			Connection closed by foreign host.
			Jelex:~ zhenhua$
		 */
	}

	private static class DaytimeThread extends Thread {

		Socket socket;

		public DaytimeThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				Writer out = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
				out.write(new Date() + "\r\n");
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
