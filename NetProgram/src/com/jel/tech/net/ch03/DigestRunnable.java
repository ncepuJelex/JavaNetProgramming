package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;
/**
 * 以Runnable的方式获取文件摘要，它和DigestThread功能一样。
 * 但是需要注意的是：线程并没有返回结果到主线程中，它只是
 * 打印出文件摘要的结果，要知道，主线程要获取线程的结果，
 * 这个是有点不好搞的！
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class DigestRunnable implements Runnable {

	private final String fileName;

	public DigestRunnable(String fileName) {
		this.fileName = fileName;
	}

	public static void main(String[] args) {
		System.out.println("请输入文件名称，以英文逗号分隔：");
		Scanner sc = new Scanner(System.in);
		String fileNames = sc.next();
		sc.close();

		if (fileNames.trim().isEmpty())
			return;
		String[] fns = fileNames.split(",");
		for(String fn : fns) {
			new Thread(new DigestRunnable(fn)).start();;
		}
	}

	@Override
	public void run() {
		FileInputStream in = null;
		DigestInputStream din = null;
		try {
			in = new FileInputStream(fileName);
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			din = new DigestInputStream(in, sha);
			while(din.read() != -1);
			byte[] digest = sha.digest();
			StringBuilder result = new StringBuilder(fileName);
			result.append(": ");
			result.append(DatatypeConverter.printHexBinary(digest));
			System.out.println(result);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if(din != null) {
				try {
					din.close();
				} catch (IOException e) {
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
