package com.jel.tech.net.ch03;

import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;
/**
 * 通过实例回调获取任务线程执行结果，看起来更顺眼些！
 * @author jelex.xu
 * @date 2017年9月3日
 */
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
			//这种方式也可以，只是：在构造方法中起线程很危险！
			//还刻Concurrent Programming中也提起过吗？
			//因为有时候构造方法还没有执行完毕，线程任务已经执行完成了！
			//然后回调，结果不就出事了嘛！
			/*new Thread(
				new InstanceCallbackDigest(fn, new InstanceCallbackDigestMain(fn)))
			.start();*/
			InstanceCallbackDigestMain digestMain = new InstanceCallbackDigestMain(fn);
			digestMain.calculateDigest();
		}
	}
	/*
	 * 不在构造方法中完成，移动到这里来
	 */
	private void calculateDigest() {
		InstanceCallbackDigest cb = new InstanceCallbackDigest(fileName, this);
		new Thread(cb).start();
	}

	public void recvDigest(byte[] digest) {
		this.digest = digest;
		//by the way, PrintStream内部已经synchronized了，所以不用我们上锁
		synchronized(System.out) {
			System.out.println(this);
		}
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
