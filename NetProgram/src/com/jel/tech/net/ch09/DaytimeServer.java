package com.jel.tech.net.ch09;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
/**
 * 时间服务器程序
 * @author jelex.xu
 * @date 2017年9月14日
 */
public class DaytimeServer {

	private static final int PORT = 1313;

	public static void main(String[] args) {

		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try(Socket connection = server.accept()) {
					Writer out = new OutputStreamWriter(connection.getOutputStream(),"ASCII");
					out.write(new Date() + "\r\n");
					out.flush();
					connection.close();
				} catch(IOException e) {}
			}
		} catch(IOException e) {
			System.err.println(e);
		}
	}
	/*
	 * running result:(在本人mac上terminal运行如下命令)
	 * Jelex:~ zhenhua$ telnet localhost 1313
		Trying ::1...
		Connected to localhost.
		Escape character is '^]'.
		Thu Sep 14 22:17:04 CST 2017
		Connection closed by foreign host.
		Jelex:~ zhenhua$
	 */
}
