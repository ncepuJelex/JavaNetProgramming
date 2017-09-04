package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
/**
 * 回调的方式，把任务线程计算文件摘要的结果返回给主线程，只是
 * 这里调用主线程类的静态方法，这好像不太好啊！
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class CallbackDigest implements Runnable {

	private String fileName;

	public CallbackDigest(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void run() {

		try {
			FileInputStream in = new FileInputStream(fileName);
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			DigestInputStream din = new DigestInputStream(in, sha);

			while(din.read() != -1) ;
			//我偷懒了，应该在finally中关闭的
			din.close();
			in.close();
			CallbackDigestMain.recvDigest(sha.digest(), fileName);

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//close din and in.
		}
	}

}
