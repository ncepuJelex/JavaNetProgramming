package com.jel.tech.net.ch03;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用ExecutorService框架执行runnable任务
 *
 * @author jelex.xu
 * @date 2017年9月4日
 */
public class GZipAllFiles {

	public static final int THREAD_COUNT = 4;

	private static final ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

	public static void main(String[] args) {

		System.out.println("please input the fileNames,comma as the split signal...");
		Scanner sc = new Scanner(System.in);
		String fileNames = sc.next();
		sc.close();

		if (fileNames.trim().isEmpty())
			return;

		String[] fns = fileNames.split(",");

		for (String fname : fns) {
			File f = new File(fname);
			if (f.exists()) {
				if (f.isDirectory()) {
					File[] files = f.listFiles();
					for (File file : files) {
						if (!file.isDirectory()) {
							Runnable task = new GZipRunnable(file);
							pool.submit(task);
						}
					}
				} else {
					Runnable task = new GZipRunnable(f);
					pool.submit(task);
				}
			}
		}
		/*
		 * This method does not abort pending jobs. It simply notifies the pool
		 * that no further tasks will be added to its internal queue and that it
		 * should shut down once it has finished all pending work
		 */
		pool.shutdown();
	}
}
