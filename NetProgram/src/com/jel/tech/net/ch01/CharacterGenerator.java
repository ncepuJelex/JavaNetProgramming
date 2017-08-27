package com.jel.tech.net.ch01;

import java.io.IOException;
import java.io.OutputStream;

public class CharacterGenerator {

	public static void main(String[] args) {

		try {
//			generateCharacters(System.out);
			generateCharacters2(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateCharacters(OutputStream out) throws IOException {

		int firstPrintableCharacter = 33;
		int numOfPrintableCharacter = 94;
		int numOfCharacterPerLine = 72;

		int start = firstPrintableCharacter;

		while(true) {

			for(int i=start; i<start+numOfCharacterPerLine; i++) {
				out.write((i-firstPrintableCharacter)%numOfPrintableCharacter + firstPrintableCharacter);
			}
			out.write('\r'); // carriage return
			out.write('\n'); // linefeed
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			start = (start + 1 - firstPrintableCharacter)%numOfPrintableCharacter + firstPrintableCharacter;
		}
	}

	public static void generateCharacters2(OutputStream out) throws IOException {

		int firstPrintableCharacter = 33;
		int numOfPrintableCharacter = 94;
		int numOfCharacterPerLine = 72;

		int start = firstPrintableCharacter;

		byte[] line = new byte[numOfPrintableCharacter + 2];

		while(true) {

			for(int i=start; i<start+numOfCharacterPerLine; i++) {
				line[i-start] = (byte) ((i-firstPrintableCharacter) % numOfPrintableCharacter + firstPrintableCharacter);
			}
			line[72] = '\r';
			line[73] = '\n';

			out.write(line);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			start = (start + 1 - firstPrintableCharacter)%numOfPrintableCharacter + firstPrintableCharacter;
		}
	}

}
