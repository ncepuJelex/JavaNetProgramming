package com.jel.tech.net.ch07;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 关注URLConnection中的encoding属性，Content-type,
 * 后面还有
 * 	Content-length
	• Content-encoding • Date
	• Last-modified
	• Expires
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class EncodingAwareSourceViewer {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			String encoding = "ISO-8859-1"; //default encoding
			URL u = new URL("https://www.jd.com/");
			URLConnection connection = u.openConnection();
			String contentType = connection.getContentType();
			int encodingStart = contentType.indexOf("charset=");
			if(encodingStart != -1) {
				encoding = contentType.substring(encodingStart+8); //charset=是8字节长
			}
			br = new BufferedReader(
					new InputStreamReader(
							new BufferedInputStream(
									connection.getInputStream()
							),
						encoding)
				);
			int c;
			while((c=br.read()) != -1) {
				System.out.print((char)c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}

	}
}
