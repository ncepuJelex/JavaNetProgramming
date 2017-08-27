package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class ReturnDigest implements Runnable {

	private String fileName;

	private byte [] digest;


	public ReturnDigest(String fileName) {
		this.fileName = fileName;
	}


	public byte[] getDigest() {
		return digest;
	}


	@Override
	public void run() {

		try {
			FileInputStream in = new FileInputStream(this.fileName);

			MessageDigest sha = MessageDigest.getInstance("SHA-256");

			DigestInputStream din = new DigestInputStream(in, sha);

			while(din.read() != -1) ;

			this.digest = sha.digest();

			din.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
