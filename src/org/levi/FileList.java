package org.levi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileList {
	BufferedWriter bw;
	int count;

	FileList() {
	}

	FileList(String target) {
		try {
			bw = new BufferedWriter(new FileWriter(target));

			count = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int listFiles(File folder, String key) throws IOException {
		try {
			System.out.println("Found Folder Named:" + folder);
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && listOfFiles[i].toString().contains("txt")) {
					search(listOfFiles[i], key);
				} else if (listOfFiles[i].isDirectory()) {
					listFiles(listOfFiles[i], key);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	void search(File file, String key) throws IOException {
		int fileCount = 0;
		String line;
		System.out.println(file.toString());
		bw.write("************************" + file.toString() + "********************************\n");
		bw.newLine();
		BufferedReader br = new BufferedReader(new FileReader(file));
		int i = 1;
		while ((line = br.readLine()) != null) {
			if (line != null && line.contains(key)) {
				bw.write("Line " + i + " : " + line);
				fileCount++;
				bw.newLine();
			}
			i++;
		}
		count = count + fileCount;
		bw.write("\n" + fileCount + " hits in " + file.toString());
		bw.write(
				"\n<------------------------------------------------ EOF ------------------------------------------------>\n\n");
		bw.newLine();
		bw.flush();

		br.close();
	}
}