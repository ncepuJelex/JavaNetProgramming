package com.jel.tech.net.ch03;

import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 我们虽然可以直接调用MaxFindTask的call方法，
 * 但那不是本意，我们把数组对半分，起2个线程，每个
 * 线程各自处理一半，找出其中最大的数，然后合并结果。
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class MultiThreadMaxFinder {

	public static void main(String[] args) {

		int [] data = {19, 7, 10, 2, 5, 66};
		try {
			int max = max(data);
			System.out.println("max number->" + max); //66
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintStream out = System.out;
		PrintStream out2 = System.out;
		System.out.println(out == out2); //true
		System.out.println(out == MaxFinderTask.out); //true
	}

	public static int max(int [] data) throws Exception {
		if(data.length == 1) return data[0];
		if(data.length == 0) throw new IllegalArgumentException();

		MaxFinderTask t1 = new MaxFinderTask(data, 0, data.length/2);
		MaxFinderTask t2 = new MaxFinderTask(data, data.length/2, data.length);

		ExecutorService service = Executors.newFixedThreadPool(2);

		Future<Integer> future1 = service.submit(t1);
		Future<Integer> future2 = service.submit(t2);
		//只有future1拿到结果才去调用future2.get(),尽管可能future2早就可以拿到结果，
		//没办法，阻塞就是这么讨厌
		return Math.max(future1.get(), future2.get());
	}
}


