package com.jel.tech.net.ch03;

import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadMaxFinder {

	public static void main(String[] args) {

		int [] data = {19, 7, 10, 2, 5, 66};

		try {
			int max = max(data);
			System.out.println("max number->" + max);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintStream out = System.out;
		PrintStream out2 = System.out;
		System.out.println(out == out2);
		System.out.println(out == MaxFinderTask.out);
	}

	public static int max(int [] data) throws Exception {

		if(data.length == 1) return data[0];

		if(data.length == 0) throw new IllegalArgumentException();


		MaxFinderTask t1 = new MaxFinderTask(data, 0, data.length/2);
		MaxFinderTask t2 = new MaxFinderTask(data, data.length/2, data.length);

		ExecutorService service = Executors.newFixedThreadPool(2);

		Future<Integer> future1 = service.submit(t1);
		Future<Integer> future2 = service.submit(t2);

		return Math.max(future1.get(), future2.get());
	}
}


