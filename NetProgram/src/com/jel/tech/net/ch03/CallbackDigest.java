package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

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

			CallbackDigestMain.recvDigest(sha.digest(), fileName);

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//close din and in.
		}
	}

}
