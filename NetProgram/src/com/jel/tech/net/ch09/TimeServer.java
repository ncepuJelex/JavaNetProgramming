package com.jel.tech.net.ch09;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 时间服务器，和上一个DaytimeServer实现细节 不同的是：该程序返回4字节、big indian/unsigned的字节数据
 * 而且返回的时间是相对1900年1月1日中午12点整之后的秒数， 而问题在于java中的Date的起始时间是1970年1月1日中午12点整，它们之间
 * 相差的秒数为2208988800L，具体如何计算出的，可参看上一章时间客户端程序.
 *
 * @author jelex.xu
 * @date 2017年9月14日
 */
public class TimeServer {

	public final static int PORT = 3737;

	public static void main(String[] args) {

		long differenceBetweenEpochs = 2208988800L;
		try (ServerSocket server = new ServerSocket(PORT)) {
			while (true) {
				try (Socket connection = server.accept()) {
					OutputStream out = connection.getOutputStream();
					Date now = new Date();
					long msSince1970 = now.getTime();
					long secondsSince1970 = msSince1970 / 1000;
					long secondsSince1900 = secondsSince1970 + differenceBetweenEpochs;
					byte[] time = new byte[4];
					time[0] = (byte) ((secondsSince1900 & 0x00000000FF000000L) >> 24);
					time[1] = (byte) ((secondsSince1900 & 0x0000000000FF0000L) >> 16);
					time[2] = (byte) ((secondsSince1900 & 0x000000000000FF00L) >> 8);
					time[3] = (byte) (secondsSince1900 & 0x00000000000000FFL);
					out.write(time);
					out.flush();
				} catch (IOException ex) {
					System.err.println(ex.getMessage());
				}
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	/*
	 * running result:
	 * Jelex:~ zhenhua$ telnet localhost 3737
		Trying ::1...
		Connected to localhost.
		Escape character is '^]'.
		?e?Connection closed by foreign host.
		Jelex:~ zhenhua$
		很奇怪哈，引用作者的话是：
		As with the TimeClient of the previous chapter,
		most of the effort here goes into working with a data format (
		32-bit unsigned integers) that Java doesn’t natively support.
		底层不支持32位长的字节！！！
	 */
}
