package com.jel.tech.net.ch09;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 一个简单的文件服务器，不管client输入什么，服务器都
 * 只返回启动是指定的文件内容
 * @author jelex.xu
 * @date 2017年9月16日
 */
public class SingleFileHttpHandler {

	private static final Logger log = Logger.getLogger("SingleFileHttpHander");

	private final byte[] header;
	private final byte[] content;
	private static final int PORT = 8080;
	private final String encoding;

	public SingleFileHttpHandler(String data, String encoding, String mimeType) throws UnsupportedEncodingException {
		this(data.getBytes(encoding), encoding, mimeType);
	}

	public SingleFileHttpHandler(byte[] data, String encoding, String mimeType) {
		this.content = data;
		this.encoding = encoding;
		String header = "HTTP/1.0 200 OK\r\n"
				+ "Server:OneFile 2.0\r\n"
				+ "Content-length: " + this.content.length + "\r\n"
				+ "Content-type: " + mimeType + "; charset=" + encoding + "\r\n\r\n";
		this.header = header.getBytes(Charset.forName("US-ASCII"));
	}

	public void start() {
		ExecutorService pool = Executors.newFixedThreadPool(100);
		try(ServerSocket server = new ServerSocket(PORT)) {
			log.info("Accepting connections on port " + PORT);
			log.info("Data to be sent:");
			log.info(new String(this.content, encoding));

			while(true) {
				try {
					Socket conn = server.accept();
					pool.submit(new HTTPHandler(conn));
				} catch (IOException e) {
					log.log(Level.WARNING, "Exception accepting connection", e);
				} catch(RuntimeException e) {
					log.log(Level.SEVERE, "Unexpected error", e);
				}
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "Could not start server.", e);
		}
	}

	private class HTTPHandler implements Callable<Void> {

		private Socket conn;

		public HTTPHandler(Socket conn) {
			this.conn = conn;
		}

		@Override
		public Void call() throws Exception {
			try {
				OutputStream out = new BufferedOutputStream(conn.getOutputStream());
				InputStream in = new BufferedInputStream(conn.getInputStream());
				//只读取client发过来的第一行数据
				StringBuilder sb = new StringBuilder();
				while(true) {
					int c = in.read();
					//一行数据到头了
					if(c == '\r' || c=='\n' || c== -1) {
						break;
					}
					sb.append(c);
				}
				//读取client第一行数据是为了判断要不要发送 http header数据过去
				if(sb.toString().indexOf("HTTP/") != -1) {
					out.write(header);
				}
				out.write(content);
				out.flush();
			} catch (IOException e) {
				log.log(Level.SEVERE, "Error Writing to client", e);
			} finally {
				conn.close();
			}
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println("输入要发送给客户端的文件：");
		Scanner sc = new Scanner(System.in);
		String filePath = sc.next();
		sc.close();
		String encoding = "UTF-8";
		try {
			//这个方法不错！
			byte[] data = Files.readAllBytes(Paths.get(filePath));
			//这个方法也不错！
			String contentType = URLConnection.getFileNameMap().getContentTypeFor(filePath);
			SingleFileHttpHandler server = new SingleFileHttpHandler(data, encoding, contentType);
			//启动server，搞起来！
			server.start();
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
	}
	/*
	 * running result:(发送文件为该项目下的a.txt,里面内容为"test SingleFileHttpHandler 服务器。")
	 * Jelex:~ zhenhua$ telnet localhost 8080
		Trying ::1...
		Connected to localhost.
		Escape character is '^]'.
		e
		test SingleFileHttpHandler 服务器。Connection closed by foreign host.
		Jelex:~ zhenhua$
	 */
}
