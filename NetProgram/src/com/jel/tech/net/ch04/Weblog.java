package com.jel.tech.net.ch04;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Scanner;

public class Weblog {

	public static void main(String[] args) {

		System.out.println("please input the fileName.");
		Scanner sc = new Scanner(System.in);

		String fileNames = sc.next();

		sc.close();

		if (fileNames.trim().isEmpty())
			return;

		String fileName = fileNames.split(",")[0];

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName)))) {

			String entry;
			while((entry = br.readLine()) != null) {
				int index = entry.indexOf(' ');
				String ip = entry.substring(0, index);
				String rest = entry.substring(index);
				//Ask DNS for the hostname
				InetAddress address = InetAddress.getByName(ip);
				System.out.println(address.getHostName() + rest);
			}

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
