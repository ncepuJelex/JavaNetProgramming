package com.jel.tech.net.ch02;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The most popular variation of this protocol sends 72-character lines
 * containing printable ASCII characters. (The printable ASCII characters are
 * those between 33 and 126 inclusive that exclude the various whitespace and
 * control characters.) The first line contains characters 33 through 104,
 * sorted. The second line contains characters 34 through 105. The third line
 * contains characters 35 through 106. This continues through line 29, which
 * contains characters 55 through 126. At that point, the characters wrap around
 * so that line 30 contains characters 56 through 126 followed by character 33
 * again.
 *
 * @author jelex.xu
 * @date 2017年9月3日
 */
public class CharacterGenerator {

	public static void main(String[] args) {

		try {
			// generateCharacters(System.out);
			generateCharacters2(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateCharacters(OutputStream out) throws IOException {

		int firstPrintableCharacter = 33; //第一个字符对应数字为33
		int numOfPrintableCharacter = 94; //共94个可打印字符ASCII码
		int numOfCharacterPerLine = 72; //一行打印72个出来

		int start = firstPrintableCharacter;

		while (true) {

			for (int i = start; i < start + numOfCharacterPerLine; i++) {
				out.write((i - firstPrintableCharacter) % numOfPrintableCharacter + firstPrintableCharacter);
			}
			out.write('\r'); // carriage return
			out.write('\n'); // line feed
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			start = (start + 1 - firstPrintableCharacter) % numOfPrintableCharacter + firstPrintableCharacter;
		}
	}

	public static void generateCharacters2(OutputStream out) throws IOException {

		int firstPrintableCharacter = 33;
		int numOfPrintableCharacter = 94;
		int numOfCharacterPerLine = 72;

		int start = firstPrintableCharacter;

		byte[] line = new byte[numOfPrintableCharacter + 2];

		while (true) {

			for (int i = start; i < start + numOfCharacterPerLine; i++) {
				line[i - start] = (byte) ((i - firstPrintableCharacter) % numOfPrintableCharacter
						+ firstPrintableCharacter);
			}
			line[72] = '\r';
			line[73] = '\n';
			//相比上一个方法，优化了下，写入一个byte数组，而不是单个字符，
			//效率高很多！
			out.write(line);
			//睡会，不能累死了CPU
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			//下一行字符打印
			start = (start + 1 - firstPrintableCharacter) % numOfPrintableCharacter + firstPrintableCharacter;
		}
	}

}
