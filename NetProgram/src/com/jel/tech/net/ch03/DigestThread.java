package com.jel.tech.net.ch03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class DigestThread extends Thread {

	private String fileName;


	public static void main(String[] args) {

		System.out.println("please input the fileNames,comma as the split signal...");
		Scanner sc = new Scanner(System.in);

		String fileNames = sc.next();

		sc.close();

		if(fileNames.trim().isEmpty()) return;

		String[] fns = fileNames.split(",");
		for(String fn : fns) {
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
			din = new DigestInputStream(in, sha);
			//指着文件结尾
			//Reads a byte, and updates the message digest (if the digest
			// function is on)
			while(din.read() != -1) ;

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
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if(din != null) {
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
