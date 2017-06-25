package com.theleapofcode.algs.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	public static String getFileContent(String fileName) {
		String content = null;
		String path = FileUtil.class.getClassLoader().getResource(fileName).getPath();
		try {
			content = FileUtils.readFileToString(new File(path), "UTF-8").replaceAll("\\s+", " ");
		} catch (IOException e) {
		}

		return content;
	}

}
