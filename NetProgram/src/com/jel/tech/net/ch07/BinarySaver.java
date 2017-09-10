package com.jel.tech.net.ch07;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * 使用http connection 下载binary数据,
 * 这里介绍了一个从网络上下载文件的通用规则
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class BinarySaver {

	public static void main(String[] args) {
		//timg?image&quality=80&size=b9999_10000&sec=1504970663373&di=8d7e6a9bfadb85ca60792377698e39ed&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201602%2F05%2F20160205185742_aLSzK.jpeg
		System.out.println("输入url");
		Scanner sc = new Scanner(System.in);
		String url = sc.next();
		sc.close();
		try {
			URL u = new URL(url);
			saveBinaryFile(u);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveBinaryFile(URL u) throws IOException {
		URLConnection uc = u.openConnection();
		String contentType = uc.getContentType();
		int contentLength = uc.getContentLength();

		if(contentType.startsWith("text/") || contentLength == -1) {
			throw new IOException("不是一个binary file,下载个毛线啊！");
		}
		try(InputStream raw = uc.getInputStream()) {
			InputStream in = new BufferedInputStream(raw);
			byte[] data = new byte[contentLength];
			int offset = 0;
			while(offset < contentLength) {
				int byteRead = in.read(data, offset, contentLength-offset);
				if(byteRead == -1) break; //出事了！
				offset += byteRead;
			}
			if(offset != contentLength) {
				throw new IOException("只读取了：" + offset + "字节数据，应该读取：" + contentLength + "字节的");
			}
			String fileName = u.getFile();
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
			try(FileOutputStream fout = new FileOutputStream(fileName)) {
				fout.write(data);
				fout.flush();
			}
		}
		/*
		 * 输入
		https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504970663373&di=8d7e6a9bfadb85ca60792377698e39ed&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201602%2F05%2F20160205185742_aLSzK.jpeg
		保存的文件名：timg?image&quality=80&size=b9999_10000&sec=1504970663373&di=8d7e6a9bfadb85ca60792377698e39ed&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201602%2F05%2F20160205185742_aLSzK.jpeg
		我请求下载的是一张c罗穿裤叉的图片，后面背着一个妹子，好像不是伊莲娜！
		输出：在项目根目录下，唯一的那张图片就是，或者对照文件名吧！很长的那个。
		 */
	}
}
