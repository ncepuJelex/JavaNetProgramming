package com.jel.tech.net.ch08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;
/**
 * 写一个可以和dict.org服务器交互的client
 * dict.org服务器上有许多字典db，可以在各种语言之间转换单词
 * 这里演示把英语单词转换成德语
 * 下面是我在terminal 上的部分使用演示
 * Jelex:~ zhenhua$ telnet dict.org 2628
	Trying 216.18.20.172...
	Connected to dict.org.
	Escape character is '^]'.
	220 pan.alephnull.com dictd 1.12.1/rf on Linux 4.4.0-1-amd64 <auth.mime> <61163475.27683.1505141136@pan.alephnull.com>
	DEFINE eng-lat gold
	550 invalid database, use SHOW DB for list
	SHOW DB（可以看到好多转换说明，比如：）
	。。。
	fd-spa-eng "Spanish-English FreeDict Dictionary ver. 0.1.1"
	fd-eng-hun "English-Hungarian FreeDict Dictionary ver. 0.1"
	fd-ita-eng "Italian-English FreeDict Dictionary ver. 0.1.1"
	fd-wel-eng "Welsh-English Freedict dictionary"
	fd-eng-nld "English-Dutch FreeDict Dictionary ver. 0.1.1"
	fd-fra-eng "French-English FreeDict Dictionary ver. 0.3.4"
	fd-tur-deu "Turkish-German FreeDict Dictionary ver. 0.1.1"
	fd-swe-eng "Swedish-English FreeDict Dictionary ver. 0.1.1"
	fd-nld-fra "Nederlands-French FreeDict Dictionary ver. 0.1.1"
	fd-eng-swa "English-Swahili xFried/FreeDict Dictionary"
	fd-deu-nld "German-Dutch FreeDict Dictionary ver. 0.1.1"
	fd-fra-deu "French-German FreeDict Dictionary ver. 0.1.1"
	fd-eng-cro "English-Croatian Freedict Dictionary"
	fd-eng-ita "English-Italian FreeDict Dictionary ver. 0.1.1"
	fd-eng-lat "English-Latin FreeDict Dictionary ver. 0.1.1"
	fd-lat-eng "Latin-English FreeDict Dictionary ver. 0.1.1"
	。。。

 * @author jelex.xu
 * @date 2017年9月11日
 */
public class DictClient {

	private static final String SERVER = "dict.org";
	private static final int PORT = 2628;
	private static final int TIMEOUT = 15000;

	public static void main(String[] args) {

		Socket s = null;
		try {
			s = new Socket(SERVER, PORT);
			s.setSoTimeout(TIMEOUT);
			OutputStream out = s.getOutputStream();
			Writer writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			InputStream in = s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			System.out.println("请输入单词，以英文逗号分隔：");
			Scanner sc = new Scanner(System.in);
			String words = sc.next();
			sc.close();
			for(String w : words.split(",")) {
				define(writer, br, w);
			}
			writer.write("quit\r\n"); //这是dict.org的规定
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//close socket
		}
		/*
		 * running result:
		 * 输入：gold,money,sex
		   输出：gold /gould/
			 Gold <n>
			money /mʌniː/
			 Geld <n>
			sex /seks/
			 Geschlecht <n>
		 */
	}

	private static void define(Writer writer, BufferedReader br, String w) throws IOException {
		writer.write("DEFINE fd-eng-deu " + w + "\r\n"); //fd-eng-deu "English-German FreeDict Dictionary ver. 0.3.6"
		writer.flush();

		for(String line=br.readLine(); line!=null; line=br.readLine()) {
			if(line.startsWith("250 ")) { //ok
				return;
			}
			else if(line.startsWith("552 ")) { //no match
				System.out.println("No definition found for " + w);
				return;
			}
			else if(line.matches("\\d\\d\\d .*")) {
				continue;
			}
			else if(line.trim().equals(".")) {
				continue;
			}
			else {
				System.out.println(line);
			}
		}
	}


}
