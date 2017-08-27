package com.jel.tech.net.ch03;

import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class InstanceCallbackDigestMain {

	private String fileName;
	private byte[] digest;

	public InstanceCallbackDigestMain(String fileName) {
		this.fileName = fileName;
	}

	public static void main(String[] args) {

		System.out.println("please input the fileNames,comma as the split signal...");
		Scanner sc = new Scanner(System.in);

		String fileNames = sc.next();

		sc.close();

		if (fileNames.trim().isEmpty())
			return;

		String[] fns = fileNames.split(",");

		for(String fn : fns) {
			new Thread(
				new InstanceCallbackDigest(fn, new InstanceCallbackDigestMain(fn)))
			.start();

		}

	}

	public void recvDigest(byte[] digest) {

		this.digest = digest;
		System.out.println(this);
	}

	@Override
	public String toString() {

		StringBuilder result = new StringBuilder(this.fileName).append(": ");
		if(this.digest != null) {
			result.append(DatatypeConverter.printHexBinary(digest));
		} else {
			result.append("digest not available yet.");
		}
		return result.toString();
	};


}
