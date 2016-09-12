package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

	public static String readFile(String path) {
		File file = new File(path);
		StringBuffer sb = new StringBuffer();
		Reader reader = null;
		try {
			// System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					// System.out.print((char) tempchar);
					sb.append((char) tempchar);
				}
			}
			// System.out.println(sb.toString());
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e1) {
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 */
	public static Map<String, String> getInputParams(String path, Map<String, String> inputParams) {
		File file = new File(path);
		File[] tempList = file.listFiles();
		Map<String, String> inParams = new HashMap<String, String>();
		if (inputParams != null) {
			inParams.putAll(inputParams);
		}
		System.out.println("该目录下对象个数：" + tempList != null ? tempList.length : tempList);
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				inParams.put(tempList[i].getName(), readFile(tempList[i]));
			}
			if (tempList[i].isDirectory()) {
				getInputParams(tempList[i].toString(), inParams);
			}
		}
		return inParams;
	}
	
	private static String readFile(File file) {
		StringBuffer sb = new StringBuffer();
		Reader reader = null;
		try {
			// System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					// System.out.print((char) tempchar);
					sb.append((char) tempchar);
				}
			}
			// System.out.println(sb.toString());
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return sb.toString();
	}

}
