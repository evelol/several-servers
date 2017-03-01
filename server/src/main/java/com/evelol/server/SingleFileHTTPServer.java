package com.evelol.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by Administrator on 2017/2/28.
 */
public class SingleFileHTTPServer extends Thread {
	byte[] content;
	byte[] header;
	int port = 80;

	public SingleFileHTTPServer(String data, String encoding, String MIMEType, int port) throws UnsupportedEncodingException {
		this(data.getBytes(encoding), encoding, MIMEType, port);
	}

	public SingleFileHTTPServer(byte[] data, String encoding, String MIMEType, int port) throws UnsupportedEncodingException {
		this.content = data;
		this.port = port;
		String header = "HTTP/1.0 200 OK\r\n" +
				"Server : One File 1.0\r\n" +
				"Content-length: " + this.content.length + "\r\n" +
				"Content-type: " + MIMEType + "\r\n\r\n";
		this.header = header.getBytes("ASCII");
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(this.port);
			System.out.println("Acceppting connections on port:" + serverSocket.getLocalPort());
			System.out.println("Data to be send:");
			//System.out.println(new String(this.content));// 中文乱码
			System.out.println(new String(this.content, Charset.forName("gb2312")));//源文件是gb2312时显示正常
			//System.out.println(new String(this.content, StandardCharsets.UTF_8));//源文件是utf-8时显示正常
			while (true) {
				Socket connection = null;
				try {
					connection = serverSocket.accept();
					OutputStream out = new BufferedOutputStream(connection.getOutputStream());
					InputStream in = new BufferedInputStream(connection.getInputStream());
					StringBuffer request = new StringBuffer();
					while (true) {
						int c = in.read();
						if (c == '\r' || c == '\n' || c == -1) {
							break;
						}
						request.append((char) c);
					}
					//如果检测到是HTTP/1.0及以后的协议，按照规范，需要发送一个MIME首部
					if (request.toString().indexOf("HTTP/")!=-1) {
						out.write(this.header);
					}
					out.write(this.content);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					if (connection!=null) {
						connection.close();
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Could not start server. Port Occupied");
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String contentType="text/plain";
			if (args[0].endsWith(".html")||args[0].endsWith(".htm")) {
				contentType="text/html";
			}

			InputStream in=new FileInputStream(args[0]);
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			int b;
			while ((b=in.read())!=-1) {
				out.write(b);
			}
			byte[] data=out.toByteArray();

			//设置监听端口
			int port;
			try {
				port=Integer.parseInt(args[1]);
				if (port<1||port>65535) {
					port=80;
				}
			} catch (Exception e) {
				port=80;
			}

			String encoding="ASCII";
			if (args.length>2) {
				encoding=args[2];
			}

			Thread t=new SingleFileHTTPServer(data, encoding, contentType, port);
			t.start();

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Usage:java SingleFileHTTPServer filename port encoding");
		}catch (Exception e) {
			System.err.println(e);// TODO: handle exception
		}
	}




}
