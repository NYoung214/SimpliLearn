package filehandling;

import java.io.*;
import java.util.Scanner;

public class ReadFile{
	
	private static final String FILE_PATH = "src/main/java/filehandling/files/";
	
	public void readFromFile() throws FileNotFoundException {
		File inputFile = new File(FILE_PATH);
		Scanner input = new Scanner(inputFile);
		
		displayFiles(inputFile);
	}

	
	public void displayFiles(File directory) {
		File fileArray[] = directory.listFiles();
		int i=1;
		for(File file : fileArray) {
			System.out.println(i+": "+file.getName());
		}
		
	}
}
