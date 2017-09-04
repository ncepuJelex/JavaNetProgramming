package com.jel.tech.net.ch03;

import java.io.PrintStream;
import java.util.concurrent.Callable;
/**
 * 为了演示使用Callable 和 ExecutorService
 * 来获取任务执行的结果，这里具体使用找出数组中最大的数为例。
 * 这样我们不用自己建立线程，处理这些细节上的东西。
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class MaxFinderTask implements Callable<Integer> {

	private int [] data;
	private int start;
	private int end;

	public static PrintStream out;
	static {
		out = System.out;
	}

	public MaxFinderTask(int[] data, int start, int end) {
		this.data = data;
		this.start = start;
		this.end = end;
	}

	@Override
	public Integer call() throws Exception {
		int max = Integer.MIN_VALUE;
		for(int i=start; i<end; i++) {
			if(data[i] > max) max = data[i];
		}
		return max;
	}
}
