package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

/**
 * 计算一个文件的摘要,digest, 因为读取文件耗时较长，所以单独起个线程去做
 * 这件事，另外，看到javax.xml.bind.DatatypeConverter吗？ 这里面用到了它的一个方法，把字节数组转换成16进制字符串，
 * 另外，看看如何获取一个文件的摘要！
 *
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class DigestThread extends Thread {

	private String fileName;

	public static void main(String[] args) {

		System.out.println("please input the fileNames,comma as the split signal...");
		Scanner sc = new Scanner(System.in);

		String fileNames = sc.next();

		sc.close();

		if (fileNames.trim().isEmpty())
			return;

		String[] fns = fileNames.split(",");
		for (String fn : fns) {
			new DigestThread(fn).start();
		}
	}

	@Override
	public void run() {
		FileInputStream in = null;
		DigestInputStream din = null;
		try {
			in = new FileInputStream(fileName);
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			// Creates a digest input stream, using the specified input stream
			// and message digest.
			din = new DigestInputStream(in, sha);
			// 指着文件结尾
			// Reads a byte, and updates the message digest (if the digest
			// function is on)
			while (din.read() != -1)
				;

			byte[] digest = sha.digest();

			StringBuilder result = new StringBuilder(fileName);
			result.append(": ");
			result.append(DatatypeConverter.printHexBinary(digest));

			System.out.println(result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (din != null) {
				try {
					din.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public DigestThread(String fileName) {
		this.fileName = fileName;
	}
}
