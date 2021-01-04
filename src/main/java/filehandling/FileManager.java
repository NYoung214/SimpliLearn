package filehandling;

import java.io.File;
import java.time.LocalDate;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
	
	List<File>fileList = new ArrayList<File>();
	private String[] poems = {"mary_little_lamb",
			"fifth_of_november",
			"itsy_bitsy_spider",
			"london_bridge",
			"twinkle_little_star"}; 
	
	private static final String FILE_PATH = "src/main/java/filehandling/files/";
	// ============= Run THESE ON STARTUP ============== //
	public void start() throws IOException {
		System.out.println("Booting up files...");
    	File inputFile = new File(FILE_PATH);
    	for(File file : inputFile.listFiles()) {
    		fileList.add(file);
    	}
    	createThree();

	}
	
	public void createThree() throws IOException {
		System.out.println("Checking for number of poems...");
		boolean mary = false;
		boolean london = false;
		boolean fifth = false;
		for(File file : fileList) {
			if(!file.getName().startsWith("poem")) {
				if(file.getName().contains("mary")) {
					mary = true;
				}
				if(file.getName().contains("london")) {
					london = true;
				}
				if(file.getName().contains("fifth")) {
					fifth = true;
				}
			}
		}
		if(!mary) {
			createFile("mary_little_lamb",RhymeString.getMary());
		}
		if(!london) {
			createFile("london_bridge",RhymeString.getLondon());
		}
		if(!fifth) {
			createFile("fifth_of_november",RhymeString.getRemember());
		}
		System.out.println("Found all five starting poems...");
	}
	
	//============= STARTUP COMPLETE RUN THE MENU LOOP ============== //
	public void menu() throws IOException {
		Scanner input = new Scanner(System.in);
		String choice;
		String answer = "";			
		while(!answer.equals("0")) {
			printDashes();
			printFiles();
			System.out.println("What would you like to do?");
			printDashes();
			System.out.println("1. Create a new file\n"
					+ "2. Read a file\n"
					+ "3. Edit a file\n"
					+ "4. Append a file\n"
					+ "5. Delete a file\n"
					+ "0. Exit Application\n");
	
			answer = input.nextLine();
			printDashes();
			if(answer.equals("0")) {
				System.out.println("Exiting application.");
				break;
			}
			switch(answer) {
			case "0":
				break;
			case "1":
				createFileOperation();
				break;
			case "2":
				System.out.println("Which file would you like to read? ");
				choice = confirmChoice(2);
				if(choice.equals("0")) {
					System.out.println("Going back to menu.");
					break;
				}
				readFileOperation(choice);
				break;
			case "3":
				System.out.println("Which file would you like to edit? ");
				choice = confirmChoice(3);
				if(choice.equals("0")) {
					System.out.println("Going back to menu.");
					break;
				}
				editFileOperation(choice);
				break;
			case "4":
				System.out.println("Which file would you like to append? ");
				choice = confirmChoice(3);
				if(choice.equals("0")) {
					System.out.println("Going back to menu.");
					break;
				}
				appendFileOperation(choice);
				break;
			case "5":
				System.out.println("Which file would you like to delete? ");
				choice = confirmChoice(3);
				if(choice.equals("0")) {
					System.out.println("Going back to menu.");
					break;
				}
				deleteFileOperation(choice);
				break;
			}			
		}//END LOOP
		input.close();
	}
	
	//============= ENSURE INPUT IS VALID ============== //
	public String confirmChoice(int n) {
		Scanner input = new Scanner(System.in);
		String choice = input.nextLine();
		while(!valueCheck(choice,n)) {
			System.out.println("Invalid choice.");
			choice = input.nextLine();
		}
		return choice;
	}
	//============= OVERLOAD WITH CONTENT LENGTH ============== //
	public String confirmLen(int n, int len) {
		Scanner input = new Scanner(System.in);
		String choice = input.nextLine();
		while(!valueCheck(choice,n,len)) {
			System.out.println("Invalid choice.");
			choice = input.nextLine();
		}
		return choice;
	}
	
	//============= TRY TO CONVERT INPUT INTO INT OR THROW ============== //
	public boolean valueCheck(String num, int n) {
		try {
			int choice = Integer.parseInt(num);
			
			switch(n) {
			case 2:
				if(choice >= 0 && choice <= fileList.size()) {
					return true;
				}				
				break;
			case 3:
				if(choice >= 0 && choice <= fileList.size()) {
					if(choice == 0 || fileList.get(choice-1).getName().startsWith("poem") ){
						return true;							
					}
					printDashes();
					System.out.println("Cannot edit, append, or delete these poems:");
					for(String poem : poems) {
						System.out.println("\t"+poem);
					}
					printDashes();
					return false;
				}	
				break;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	//============= TRY TO CONVERT INPUT INTO INT AND USE LENGTH TO VALIDATE ============== //
	public boolean valueCheck(String num, int n, int len) {
		try {
			int choice = Integer.parseInt(num);
			
			switch(n) {
			case 31:
				if(choice >= 1 && choice <= len) {
					return true;
				}				
				break;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	//============= READ FILE FUNCTIONS ============== //
	public void readFileOperation(String choice) throws FileNotFoundException {
		File file = fileList.get(Integer.parseInt(choice)-1);
		Scanner reader = new Scanner(file);
		String line;
		int len = 0;
		int i = 1;
		while(reader.hasNextLine()) {
			line = reader.nextLine();
			if(len < line.length()) {
				len = line.length();
			}
		}
		len += 10;
		reader.close();
		reader = new Scanner(file);
		printDashes(len);
		while(reader.hasNextLine()) {
			line = reader.nextLine();
			System.out.printf("|%3d: %s",i,line);
			printGap(line.length(),len);
			i++;
		}
		printDashes(len);
		reader.close();
	}
	
	//============= READ FILE RETURN NUMBER OF LINES ============== //
	public int getLineNumber(String choice) throws FileNotFoundException {
		File file = fileList.get(Integer.parseInt(choice)-1);
		Scanner reader = new Scanner(file);
		String line;
		int len = 0;
		int i = 1;
		while(reader.hasNextLine()) {
			line = reader.nextLine();
			if(len < line.length()) {
				len = line.length();
			}
		}
		len += 10;
		reader.close();
		reader = new Scanner(file);
		printDashes(len);
		while(reader.hasNextLine()) {
			line = reader.nextLine();
			System.out.printf("|%3d: %s",i,line);
			printGap(line.length(),len);
			i++;
		}
		printDashes(len);
		reader.close();
		return i-1;
	}
	
	//============= EDIT FILE FUNCTIONS ============== //
	public void editFileOperation(String choice) throws IOException {
		Scanner input = new Scanner(System.in);
		int len = getLineNumber(choice);
		System.out.println("Which line would you like to edit? ");			
		String line = confirmLen(31,len);
		System.out.println("Enter new line: ");
		String newLine = input.nextLine();
		int fileNum = Integer.parseInt(choice)-1;
		int lineNum = Integer.parseInt(line);
		replaceLine(fileNum,newLine,lineNum);
		readFileOperation(choice);
	}

	private void replaceLine(int choice, String newLine, int lineNum) throws IOException {
		Scanner reader = new Scanner(fileList.get(choice));
		StringBuilder content = new StringBuilder();
		int i =0;
		while(i < lineNum-1 && reader.hasNextLine()) {
			content.append(reader.nextLine()+"\n");
			i++;
		}
		// Here is where the new line should be inserted
		content.append(newLine+"\n");
		//throw away next line
		reader.nextLine();
		while(reader.hasNextLine()) {
			content.append(reader.nextLine()+"\n");
		}
		//System.out.print(content.toString());
		editFile(fileList.get(choice).getName(),content.toString());
	}
	// ===== APPEND FILE FUNCTIONS ===== //
	public void appendFileOperation(String choice) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter new line: ");
		String newLine = input.nextLine();
		int fileNum = Integer.parseInt(choice)-1;
		addLine(fileNum,newLine);
		readFileOperation(choice);
	}
	
	private void addLine(int choice, String line) throws IOException {
		FileOutputStream output = new FileOutputStream(FILE_PATH+fileList.get(choice).getName(),true );
		StringBuilder content = new StringBuilder();
		content.append(line+"\n");
		output.write(content.toString().getBytes());
	}
	
	//============= CREATE FILE FUNCTIONS ============== //
	public void createFileOperation() throws IOException {
		Scanner input = new Scanner(System.in);
		int len = fileList.size()+1;
		int cutoff = 0;
		StringBuilder content = new StringBuilder();
		System.out.println("Enter your poem:"
				+ "\n- when finished, enter \"-exit\" to exit"
				+ "\n- or type -cancel to cancel\n");
		boolean exit = false;
		boolean submit = false;
		while(!exit) {
			content.append(input.nextLine()+"\n");
			if(content.indexOf("-exit") > -1) {
				cutoff = content.indexOf("-exit");
				submit = true;
				exit = true;
				break;
			}
			if(content.indexOf("-cancel") > -1) {
				submit = false;
				exit = true;
				break;
			}
		}
		if(submit) {
			content.delete(cutoff,content.length());
			String filename = "poem-"+len+"-date-"+LocalDate.now();
			createFile(filename,content.toString());
		}
		else {
			content.delete(0,content.length());
			System.out.println("Cancelling file creation.");
		}
	}
	
	public void createFile(String filename, String content) throws IOException {
		File file = new File(FILE_PATH+filename+".txt");
		fileList.add(file);
		writeFile(filename, content);
		System.out.println(filename+" has been created");
	}
	
	public void writeFile(String filename, String content) throws IOException {
		FileWriter writer = new FileWriter(FILE_PATH+filename+".txt");
		System.out.println("writing content inside "+filename);
		writer.write(content);
		writer.close();
	}
	
	// use this if there is a .txt attached
	public void editFile(String filename, String content) throws IOException {
		FileWriter writer = new FileWriter(FILE_PATH+filename);
		System.out.println("writing content inside "+filename);
		writer.write(content);
		writer.close();
	}
	
	// ============ DELETE FILE FUNCTIONS =========== //
	public void deleteFileOperation(String choice) {
		int num = Integer.parseInt(choice)-1;
		File file = new File(FILE_PATH+fileList.get(num).getName());
		if(file.delete()) {
			System.out.println(fileList.get(num).getName() + " has been deleted.");
			fileList.remove(num);
		}else {
			System.out.println("File does not exist.");
		}
	}
	
	//============= PRINT LIST OF FILES IN DIRECTORY ============== //
	public void printFiles() {
		System.out.println("List of poems in directory");
		printDashes();
		for(int i=0; i<fileList.size(); i++) {
			System.out.println((i+1)+": "+fileList.get(i).getName());
		}
	}
	
	//============= GENERIC DASHES ============== //
	private void printDashes() {
		for(int i = 0; i < 40; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	//============= OVERLOAD DASH PRINTING WITH AN ACTUAL VALUE ============== //
	private void printDashes(int len) {
		for(int i = 0; i < len; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	//============= FILL IN GAPS FOR PRINTING TO MAKE IT LOOK LIKE A PAGE ============== //
	private void printGap(int lineLen, int len) {
		int print = len - lineLen - 6;
		for(int i = 0; i < print-1; i++) {
			System.out.print(" ");
		}
		System.out.println("|");
	}
}
