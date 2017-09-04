package com.jel.tech.net.ch03;

import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

/**
 * 静态方法回调的方式获取任务计算文件摘要结果
 * @author jelex.xu
 * @date 2017年9月3日
 */
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
	/*
	 * 任务线程会调用该方法，当它计算完成任务时，把计算摘要结果
	 * 通过回调返回给这个主线程
	 */
	public static void recvDigest(byte[] digest, String fileName) {
		StringBuilder result = new StringBuilder(fileName).append(": ");
		result.append(DatatypeConverter.printHexBinary(digest));
		System.out.println(result);
	}
}
