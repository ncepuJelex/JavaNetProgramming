package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
/**
 * 实例回调更通用！任务线程拿到计算结果后，调用主线程的
 * 实例方法，这个主线程实例是通过参数传递到任务线程的。
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class InstanceCallbackDigest implements Runnable {

	private String fileName;
	private InstanceCallbackDigestMain callback;

	public InstanceCallbackDigest(String fileName, InstanceCallbackDigestMain callback) {
		this.fileName = fileName;
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			FileInputStream in = new FileInputStream(fileName);
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			DigestInputStream din = new DigestInputStream(in, sha);

			while (din.read() != -1) ;
			in.close();
			din.close();
			callback.recvDigest(sha.digest());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close din and in.
		}
	}

}
