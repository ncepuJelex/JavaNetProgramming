package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
/**
 * 保存文件摘要的结果模型，有2个成员变量，一个用来接收文件名称，
 * 另一个用来保存文件摘要结果
 * @author jelex.xu
 * @date 2017年9月3日
 */
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
