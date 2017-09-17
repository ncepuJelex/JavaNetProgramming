package com.jel.tech.net.ch10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Scanner;

/**
 * char generator, 和第2章中的
 * CharacterGenerator一样，只不过，这个类
 * 从一个服务器上获取这些可打印字符，nio开始了！
 * @author jelex.xu
 * @date 2017年9月16日
 */
public class CharGenClient {

	public static final int PORT = 1919;

	public static void main(String[] args) {

		System.out.println("输入服务器host:");
		Scanner sc = new Scanner(System.in);
		String host = sc.next();
		sc.close();
		try {
			SocketAddress address = new InetSocketAddress(host, PORT);
			SocketChannel channel = SocketChannel.open(address);
			ByteBuffer buf = ByteBuffer.allocate(74); //72个字符和\r\n
			//就这样把channel和输出流绑定一起了
			WritableByteChannel out = Channels.newChannel(System.out);
			while(channel.read(buf) != -1) {
				buf.flip();
				out.write(buf); //把buf中的内容输出到out中
				buf.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * running result:（正常应该是如下结果，但因为是国外网站，访问不通，所以一直卡住了直到超时……）
	 * 等下试下自己的chargen server.结果是可以的！
	 * 输入：rama.poly.edu
	 * 输出：!"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefg
	    !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefgh
	    "#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghi
	    #$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghij
	    $%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijk
	    %&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijkl
	    &'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklm
	    ...
	 */
}
