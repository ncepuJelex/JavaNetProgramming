package com.jel.tech.net.ch10;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class OptionSupport {

	public static void main(String[] args) throws IOException {
		//设置option
		//NetworkChannel channel = SocketChannel.open();
	    //channel.setOption(StandardSocketOptions.SO_LINGER, 240);
		printOptions(SocketChannel.open());
		printOptions(ServerSocketChannel.open());
		printOptions(AsynchronousSocketChannel.open());
		printOptions(AsynchronousServerSocketChannel.open());
		printOptions(DatagramChannel.open());
		/*
		 * running result:
		 * SocketChannelImpl supports:
			SO_SNDBUF: 131072
			TCP_NODELAY: false
			IP_TOS: 0
			SO_OOBINLINE: false
			SO_REUSEADDR: false
			SO_KEEPALIVE: false
			SO_LINGER: -1
			SO_RCVBUF: 131072

			ServerSocketChannelImpl supports:
			SO_REUSEADDR: true
			SO_RCVBUF: 131072
			Exception in thread "main" java.lang.AssertionError: Option not found
				at sun.nio.ch.Net.getSocketOption(Net.java:360)
				at sun.nio.ch.ServerSocketChannelImpl.getOption(ServerSocketChannelImpl.java:177)
				at com.jel.tech.net.ch10.OptionSupport.printOptions(OptionSupport.java:25)
				at com.jel.tech.net.ch10.OptionSupport.main(OptionSupport.java:16)

		 */
	}

	private static void printOptions(NetworkChannel channel) throws IOException {
		System.out.println(channel.getClass().getSimpleName() + " supports:");
		for (SocketOption<?> option : channel.supportedOptions()) {
			System.out.println(option.name() + ": " + channel.getOption(option));
		}
		System.out.println();
		channel.close();
	}
}
