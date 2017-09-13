package com.jel.tech.net.ch08;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * 扫描机器上有哪些服务以及对应的端口
 * 我机器才只有这么一个服务端口，真是的……
 * @author jelex.xu
 * @date 2017年9月11日
 */
public class LowPortScanner {

	public static void main(String[] args) {

		for(int i=1; i<1024; i++) {
			try {
				Socket s = new Socket("localhost", i);
				System.out.println("There is a server on port " + i + " of localhost");
				s.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				//must not be a server on this port
			}
		}
		/*
		 * running result:
		 * There is a server on port 22 of localhost
		 */
	}
}
