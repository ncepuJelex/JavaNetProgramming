package com.jel.tech.net.ch03;

import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;
/**
 * 获取单独计算文件摘要的线程的结果，但是因为当主线程去
 * 获取任务线程执行的结果时，任务线程不一定已经完成了任务，提供
 * 结果给主线程，所以here is the problem.
 * 通过让线程沉睡或者 join() 的方式可以拿到结果，但是下面介绍
 * 的callback方式更简单些，也更高效！
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class ReturnDigestMain {
	/*
	 * 报错正常，因为线程结果还没有返回呢，如果你打断点，debug模式跑的话不会error,
	 * 或者让 main thread sleep 3s.
	 */
	public static void main(String[] args) throws InterruptedException {

		System.out.println("please input the fileNames,comma as the split signal...");
		Scanner sc = new Scanner(System.in);
		String fileNames = sc.next();
		sc.close();

		if (fileNames.trim().isEmpty())
			return;
		String[] fns = fileNames.split(",");
		for (String fn : fns) {
			ReturnDigest rd = new ReturnDigest(fn);
			new Thread(rd).start();

			StringBuilder result = new StringBuilder(fn).append(": ");
			Thread.sleep(3000);
			byte[] digest = rd.getDigest();
			result.append(DatatypeConverter.printHexBinary(digest));

			System.out.println(result);
		}
	}

	/**
	 * 如果你运气好可能不会报错
	 * @throws InterruptedException
	 */
	@Test
	public void fun() throws InterruptedException {
		System.out.println("please input the fileNames,comma as the split signal...");
		Scanner sc = new Scanner(System.in);
		String fileNames = sc.next();
		sc.close();

		if (fileNames.trim().isEmpty())
			return;
		String[] fns = fileNames.split(",");
		ReturnDigest[] rds = new ReturnDigest[fns.length];
		Thread[] threads = new Thread[fns.length];

		int i = 0;
		for (String fn : fns) {
			rds[i] = new ReturnDigest(fn);
			(threads[i] = new Thread(rds[i++])).start();
		}

		for (int j = 0; j < fns.length; j++) {
			//Waits for this thread to die,它和主线程共享数据，
			//该线程挂了之后，说明执行完成，获取被打断？主线程就可以
			//看到任务执行结果，并获取了！
			threads[j].join(); //有了这个，不会报错！
			StringBuilder result = new StringBuilder(fns[j]).append(": ");

			byte[] digest = rds[j].getDigest();
			result.append(DatatypeConverter.printHexBinary(digest));

			System.out.println(result);
		}
	}

	/*
	 * 这回没毛病~?还是有，卡死了,
	 * this solution is not guaranteed to work. On some virtual
	 * machines, the main thread takes all the time available and leaves no time
	 * for the actual worker threads. The main thread is so busy checking for
	 * job completion that there’s no time left to actually complete the job!
	 */
	@Test
	public void fun2() {
		System.out.println("please input the fileNames,comma as the split signal...");
		Scanner sc = new Scanner(System.in);
		String fileNames = sc.next();
		sc.close();

		if (fileNames.trim().isEmpty())
			return;
		String[] fns = fileNames.split(",");
		ReturnDigest[] rds = new ReturnDigest[fns.length];
		int i = 0;
		for (String fn : fns) {
			rds[i] = new ReturnDigest(fn);
			new Thread(rds[i++]).start();
		}

		for (int j = 0; j < fns.length; j++) {
			while (true) {
				byte[] digest = rds[j].getDigest();
				if (digest != null) {
					StringBuilder result = new StringBuilder(fns[j]).append(": ");
					result.append(DatatypeConverter.printHexBinary(digest));
					System.out.println(result);
					break;
				}
				//让线程睡会就好了！
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
