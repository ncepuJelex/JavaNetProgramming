package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

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

			callback.recvDigest(sha.digest());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close din and in.
		}
	}

}
