package com.jel.tech.net.ch09;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 起一个服务，端口随意，所以你也不知道使用了哪个端口服务监听，
 * 但是有办法！
 * @author jelex.xu
 * @date 2017年9月16日
 */
public class RandomPort {

	public static void main(String[] args) {
		//传参0表示随机端口，我不管了！
		try {
			ServerSocket s = new ServerSocket(0);
			System.out.println("This server runs on port " + s.getLocalPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * running result:
	 * This server runs on port 49459
	 * 注意哦，每次跑的结果可能不一样的，我第二次跑的结果端口是49460，顺序递增的。
	 * 另外，如果server socket没有绑定端口，那么返回-1，
	 * server socket的toString()返回格式如下：
	 * ServerSocket[addr=0.0.0.0,port=0,localport=5776]，debug时候用用，其它没卵用。
	 */
}
