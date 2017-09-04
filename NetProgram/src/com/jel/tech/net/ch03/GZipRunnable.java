package com.jel.tech.net.ch03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
/**
 * 压缩文件的runnable任务程序，
 * 这里使用了try with resource方式
 * @author jelex.xu
 * @date 2017年9月4日
 */
public class GZipRunnable implements Runnable {

	private final File input;

	public GZipRunnable(File input) {
		this.input = input;
	}

	@Override
	public void run() {

		if(!input.getName().endsWith(".gz")) {
			//压缩文件名称为原文件名称加上.gz后缀，并且在同一目录
			File outFile = new File(input.getParent(), input.getName()+".gz");
			//压缩文件不存在，我们才要去压缩啊！
			if(!outFile.exists()) {
				try (
					InputStream in = new BufferedInputStream(new FileInputStream(input));
					OutputStream out = new BufferedOutputStream(
							new GZIPOutputStream(new FileOutputStream(outFile)));
				) {
					int b;
					while((b=in.read()) != -1) {
						out.write(b);
						out.flush();
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Finished gzip file->" + input.getName());
	}

}
