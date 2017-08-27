package com.jel.tech.net.ch03;

import java.io.PrintStream;
import java.util.concurrent.Callable;

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
