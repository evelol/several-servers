package com.evelol.server;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/9.
 */
public class FileLab {
	public static void main(String[] args) throws Exception {
		System.out.println(File.pathSeparator);
		System.out.println(File.separator);
		System.out.println(File.separatorChar);
		System.out.println(File.pathSeparatorChar);
		File[] files = File.listRoots();
		System.out.println(Arrays.toString(files));
		String[] list = files[0].list();
		for (String f : list) {
			File file = new File(files[0].getCanonicalPath() + f);
			if(file.isDirectory()){
				System.out.println(file.getPath()+"\\: dir");
			} else {
				System.out.println(file.getPath()+": file");
			}
		}
		// FileFilter
		file();
		//processorIo();
		stream();
	}
	public static void file() throws IOException {
		File baseFile = new File("c:\\");
		String[] list = baseFile.list(new FilenameFilter() {
			Pattern pattern = Pattern.compile("test.txt");
			@Override
			public boolean accept(File dir, String name) {
				Matcher matcher = pattern.matcher(name);
				return matcher.find();
			}
		});
		String s = baseFile.getPath() + "\\" + list[0];
		System.out.println(s);
		//File testFile = new File(s);
		//File newFile = new File(baseFile.getPath() + "\\" + "newFile.txt");
		//newFile.createNewFile();
		//testFile.renameTo(newFile);

		Properties properties = System.getProperties();
		properties.list(System.out);
		//sun.jnu.encoding
		String o = (String)System.getProperties().get("sun.jnu.encoding");
		System.out.println(o);


	}

	public static void processorIo () throws IOException {
		List<String> a = new ArrayList<String>(Arrays.asList(new String[3]));
		Process process = Runtime.getRuntime().exec("cmd java -version");
		InputStreamReader reader = new InputStreamReader(process.getInputStream(), Charset.forName("gb2312"));
		//BufferedReader rr = new BufferedReader(reader);

		while (true) {
			char s  = (char)reader.read();
			//char read = (char)reader.read();
			System.out.print(s);
		}
	}

	public static void stream () throws InterruptedException, FileNotFoundException {
		//byte[] bytes = new byte[255];
		String abc = "this is a good reason 中国";
		byte[] bytes = abc.getBytes(StandardCharsets.UTF_16);
//		for (int i = 0; i < bytes.length; i++) {
//			bytes[i] = (byte)i;
//		}
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		while (true){
			int read = stream.read();
			if (read < 0){
				break;
			}
			read = read & 0xff;
			Thread.sleep(10);
			System.out.println(Integer.toHexString(read));
		}
		System.out.println("-----------------------------------------");

		// String
		//String abc = "this is a good reason 中国";
		StringBufferInputStream stringBufferInputStream = new StringBufferInputStream(abc);
		while (true){
			int read = stringBufferInputStream.read();
			if (read < 0){
				break;
			}
			read = read & 0xff;
			Thread.sleep(100);
			System.out.println(Integer.toHexString(read));
		}
		System.out.println("-----------------------------------------");
		FileInputStream fileInputStream = new FileInputStream("c:\\test.txt");

		System.out.println("-----------------------------------------");



	}



}
