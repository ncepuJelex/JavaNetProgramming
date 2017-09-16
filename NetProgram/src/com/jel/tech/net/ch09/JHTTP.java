package com.jel.tech.net.ch09;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 一个多功能服务器，但是如果想大用，需处理缓存，支付java servlet等等，
 * 所以任重而道远。
 * @author jelex.xu
 * @date 2017年9月16日
 */
public class JHTTP {

	private static final Logger log = Logger.getLogger(JHTTP.class.getCanonicalName());

	private static final int NUM_THREADS = 50;
	private static final String INDEX_FILE = "index.html";

	private final File rootDir;
	private static final int PORT = 8082;

	public JHTTP(File dir) throws IOException {
		if(!dir.isDirectory()) {
			throw new IOException(dir + "does not exisit as a directory.");
		}
		this.rootDir = dir;
	}

	public void start() {
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		try(ServerSocket server = new ServerSocket(PORT)) {
			while(true) {
				try{
					Socket req = server.accept();
					Runnable r = new RequestProcess(rootDir, INDEX_FILE, req);
					pool.submit(r);
				} catch(IOException e) {
					log.log(Level.WARNING, "Error accepting connection", e);
				}
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "could not start server", e);
		} catch(RuntimeException e) {
			log.log(Level.SEVERE, "could not start server~", e);
		}
	}

	public static void main(String[] args) {
		System.out.println("输入根文件：");
		Scanner sc = new Scanner(System.in);
		String rootDir = sc.next();
		sc.close();
		try {
			JHTTP webserver = new JHTTP(new File(rootDir));
			webserver.start();
		} catch (IOException e) {
			log.log(Level.SEVERE, "server could not start.", e);
		}
		/*
		 * running result:
		 * 输入根文件：
			/Users/zhenhua/Java/project/JavaNetProgramming/NetProgram/
		 * Jelex:~ zhenhua$ telnet localhost 8082
			Trying ::1...
			Connected to localhost.
			Escape character is '^]'.
			GET /a.txt HTTP/1.1
			HTTP/1.0 200 OK
			Date: Sat Sep 16 16:16:51 CST 2017
			Server: JHTTP 2.0
			Content-length: 39
			Content-type: text/plain

			test SingleFileHttpHandler 服务器。Connection closed by foreign host.
			Jelex:~ zhenhua$
		 */
	}
}
