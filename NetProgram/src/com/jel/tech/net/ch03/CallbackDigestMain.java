package com.jel.tech.net.ch03;

import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class CallbackDigestMain {

	public static void main(String[] args) {

		System.out.println("please input the fileNames,comma as the split signal...");
		Scanner sc = new Scanner(System.in);

		String fileNames = sc.next();

		sc.close();

		if (fileNames.trim().isEmpty())
			return;

		String[] fns = fileNames.split(",");

		for(int i=0; i<fns.length; i++) {
			new Thread(new CallbackDigest(fns[i])).start();
		}

	}

	public static void recvDigest(byte[] digest, String fileName) {

		StringBuilder result = new StringBuilder(fileName).append(": ");
		result.append(DatatypeConverter.printHexBinary(digest));
		System.out.println(result);
	}

}
