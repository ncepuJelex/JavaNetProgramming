package com.jel.tech.net.ch03;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

		for(String fname : fns) {
			File f = new File(fname);
			if(f.exists()) {
				if(f.isDirectory()) {
					File[] files = f.listFiles();
					for(File file : files) {
						if(!file.isDirectory()) {
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

		pool.shutdown();
	}
}
