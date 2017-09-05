package com.jel.tech.net.ch04;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 利用ExecutorService结合线程池来解析ip地址，分析日志，
 * 注意这里的数据结构,LogEntry，它保存了原本的日志字符串，
 * 另外保存了Future，万一future.get()失败，还可以catch住，
 * 退而取原本字符串日志
 * @author jelex.xu
 * @date 2017年9月5日
 */
public class PoolWeblog {

	private static final int NUM_THREAD = 4;

	public static void main(String[] args) {

		ExecutorService service = Executors.newFixedThreadPool(NUM_THREAD);

		Queue<LogEntry> results = new LinkedList<LogEntry>();

		System.out.println("please input the fileName.");
		Scanner sc = new Scanner(System.in);
		String fileNames = sc.next();
		sc.close();

		if (fileNames.trim().isEmpty())
			return;
		String fileName = fileNames.split(",")[0];

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName)))) {

			String entry;
			while((entry = br.readLine()) != null) {
				LookupTask task = new LookupTask(entry);
				Future<String> future = service.submit(task);
				LogEntry result = new LogEntry(entry,future);
				results.add(result);
			}
			// Start printing the results. This blocks each time a result isn't ready.
			for(LogEntry result : results) {
				try {
					System.out.println(result.future.get());
				} catch (Exception e) {
					System.out.println(result.original);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static class LogEntry {

		final String original;

		final Future<String> future;

		public LogEntry(String original, Future<String> future) {
			this.original = original;
			this.future = future;
		}


	}
}
