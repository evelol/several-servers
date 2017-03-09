package com.evelol.server.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;


/**
 * Created by Administrator on 2017/3/8.
 */
public class NioServer {


	public static class AcceptThread implements Runnable {

		Selector selector;
		ServerSocketChannel serverSocketChannel;
		ServerSocket serverSocket;
		boolean stopped_ = false;


		public AcceptThread(int port) throws IOException {
			this.selector = SelectorProvider.provider().openSelector();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			// Make server socket
			serverSocket = serverSocketChannel.socket();
			// Prevent 2MSL delay problem on server restarts
			serverSocket.setReuseAddress(true);
			// Bind to listening port
			serverSocket.bind(new InetSocketAddress(port));
			serverSocket.setSoTimeout(0);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		}

		@Override
		public void run() {
			while (true){

				try {
					selector.select();
					Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
					while (!stopped_ && selectedKeys.hasNext()) {
						SelectionKey key = selectedKeys.next();
						selectedKeys.remove();
						
						// if the key is marked Accept, then it has to be the server
						// transport.
						if (key.isAcceptable()) {
							SocketChannel acceptSocketChannel = serverSocketChannel.accept();
							SelectionKey clientKey = acceptSocketChannel.register(selector, SelectionKey.OP_READ);
						} else if (key.isReadable()) {

						} else if (key.isWritable()) {
							// deal with writes
							handleWrite(key);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}


			}

		}

		private void handleWrite(SelectionKey key) {
		}

		private void handleRead(SelectionKey key) {
		}

		private void handleAccept() {
		}
	}
	

	public static void main(String[] args) {
		try {
			new Thread(new AcceptThread(8081)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
